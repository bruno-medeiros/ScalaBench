package cats_examples

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContextExecutor

import cats.effect.Blocker
import cats.effect.IO
import doobie.implicits._
import doobie.util.transactor.Transactor
import doobie.util.transactor.Transactor.Aux
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

case class Foo(num: Int, name: String)

class DoobieExample extends AnyFreeSpec with Matchers with ScalaFutures {

  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  // We need a ContextShift[IO] before we can construct a Transactor[IO]. The passed ExecutionContext
  // is where nonblocking operations will be executed. For testing here we're using a synchronous EC.
  implicit val cs = IO.contextShift(ec)

  val useH2 = true
  val (driver, dburl) =
    if (useH2)
      ("org.h2.Driver", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
    else
      ("org.postgresql.Driver", "jdbc:postgresql:postgres")

  // A transactor that gets connections from java.sql.DriverManager and executes blocking operations
  // on an our synchronous EC. See the chapter on connection handling for more info.
  val xa: Aux[IO, Unit] = Transactor.fromDriverManager[IO](
    driver, // driver classname
    dburl, // connect URL (driver-specific)
    "postgres", // user
    "", // password
    Blocker.liftExecutionContext(ec),
  )

  "example" in {

    sql"select 123, 'abc'; SELECT 123, 'abcdef'"
      .query[(Int, String)]
      .option
      .transact(xa)
      .unsafeRunSync
      .foreach(println)

    sql"select 123, 'abc'"
      .query[Foo]
      .to[Vector]
      .transact(xa)
      .unsafeRunSync
      .foreach(println)

    sql"select 123, 'abc'"
      .query[Foo]
      .to[Vector]
      .map[String](e => throw new Exception("boom"))
      .transact(xa)
      .unsafeToFuture()
      .failed
      .futureValue
      .getMessage should equal("boom")

  }

  "transact example" in {

    sql"SET TRANSACTION ISOLATION LEVEL READ COMMITTED; SELECT 42".update.run
      .transact(xa)
      .unsafeRunSync

  }

}
