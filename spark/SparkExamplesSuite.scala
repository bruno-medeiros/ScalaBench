package observatory

import org.apache.spark.rdd.RDD
import org.apache.spark.sql._
import org.apache.spark.sql.expressions.scalalang.typed
import org.apache.spark.sql.types.{StructField, _}
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.{BeforeAndAfterAll, FunSuite, Ignore, Outcome}

import scala.io.StdIn


case class Info(name: String, age: Int)


@Ignore
class SparkExamplesSuite extends FunSuite with TypeCheckedTripleEquals with BeforeAndAfterAll {

  def assertSameElements[T, U](coll: Traversable[T], expected: Traversable[U])(implicit ev: =:=[T, U]): Unit = {
    assert(coll.toSet == expected.toSet)
  }

  import org.apache.log4j.{Level, Logger}
  Logger.getLogger("org.apache.spark").setLevel(Level.INFO)

  val spark: SparkSession =
    SparkSession
      .builder()
      .appName("Time Usage")
      .config("spark.master", "local[*]")
      .getOrCreate()

  import spark.implicits._

  /// ------ groupBy

  val avgData = Seq(
    (10, "A", 10.0d),
    (10, "A", 20.0d),
    (22, "A", 1000d),
    (22, "A", 1500d),
    (10, "B", 20d)
  )

  val expectedAvgData = Seq(
    (10, "A", 15.0d),
    (22, "A", 1250d),
    (10, "B", 20d)
  )

  test("groupBy & aggr (RDD)") {

    val sc = spark.sparkContext
    val rows: RDD[(Int, String, Double)] = sc.parallelize(avgData)

    val resultData = rows
      .groupBy(t => (t._1, t._2))
      .mapValues(iter => iter.map(_._3).sum / iter.size)

    val result = resultData.collect()
    assertSameElements(result, expectedAvgData.map(r => ((r._1, r._2), r._3)))
  }

  test("groupBy & aggr (DataFrame)") {

    val schema = StructType(Seq(
      StructField("id1", IntegerType),
      StructField("id2", StringType),
      StructField("money", DoubleType)
    ))

    val sc = spark.sparkContext
    val rows: RDD[Row] = sc.parallelize(avgData).map(Row.fromTuple)

    val resultData = spark.createDataFrame(rows, schema)
        .groupBy("id1", "id2")
        .agg(functions.avg("money"))

    resultData.printSchema()

    val result = resultData.collect()
    assertSameElements(result, expectedAvgData.map(Row.fromTuple))
  }


  // Same as above but with DataSet API
  test("groupBy & aggr (DataSet)") {

    val resultData = spark.createDataset(avgData)
      .groupByKey(r => (r._1, r._2))
      .agg(typed.avg[(_,_,Double)](_._3).name("moneyAvg"))

    resultData.printSchema()
    val result = resultData.collect()
    assertSameElements(result, expectedAvgData.map(r => ((r._1, r._2), r._3)))
  }

  // ---------------

  test("join") {

    val dataSet = spark.createDataset(Seq[(Int, String, Info)](
      (10, "A", Info("caine", 18)),
      (22, "A", Info("Anna", 18)),
      (10, "B", Info("mike", 21))
    ))
        .withColumnRenamed("_3", "info")

    dataSet.printSchema()

    val dataSet2 = spark.createDataset(Seq(
      (10, "A", 10.0d),
      (10, "A", 15.0d),
      (22, "A", 1000d),
      (22, "A", 1400d),
      (10, "B", 20d)
    ))
      .withColumnRenamed("_3", "money")

    val result = dataSet
      .join(dataSet2, Seq("_1", "_2"))
      .select($"info".as[Info], $"money".as[Double])

    result.printSchema()

    result.cache().collect()

    result.show()
  }

  // ------------------------

  override protected def withFixture(test: NoArgTest): Outcome = {
    println(s"--------- ${test.name}")
    super.withFixture(test)
  }

  override protected def afterAll(): Unit = {
    super.afterAll()
    // Await for UI to be accessible
    println(s">>> Press Enter to continue ")
    val _ = StdIn.readLine()
  }
}