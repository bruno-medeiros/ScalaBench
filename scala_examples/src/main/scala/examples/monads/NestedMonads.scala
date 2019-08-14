package examples.monads

import scala.concurrent.{Await, Future}
import scala.util.Try

//noinspection OptionEqualsSome
object NestedMonads extends App {

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._

  def await[T](future: Future[T]): T = {
    Await.result(future, 1.seconds)
  }

  {
    val f1 = Future { Some(123) }

    assert(await(futureAndOption(Future{ None })) == None)
    assert(await(futureAndOption(f1)) == Some("123:logged"))


    def futureAndOption(f: Future[Option[Int]]): Future[Option[String]] = {
      for {
        f1ResultOpt <- f
      } yield for {
        f1Result <- f1ResultOpt
      } yield f1Result.toString ++ ":logged"
    }
  }

  {
    val optNumber = Some(123)
    def numberToDigit(value: Int): Option[Seq[Int]] =
      Some(value.toString.split("").toSeq.flatMap(s => Try(s.toInt).toOption))

    for {
      number <- optNumber
      digits <- numberToDigit(number)
    } yield digits


    for {
      number <- optNumber
    } yield number.toString.split("").toSeq.flatMap(s => Try(s.toInt).toOption)

  }



  // concurrent async monad
  {
    val f1 = Future { Thread.sleep(100); "Foo" }
    val f2 = Future { Thread.sleep(100); "Bar" }

    val resultFt: Future[String] = for {
      v1 <- f1
      v2 <- f2
    } yield v1 ++ v2

    assert(await(resultFt) == "FooBar")


    // Using sequence: not as general tho
    val resultWithSeq = Future.sequence(Seq(f1, f2))
      .map(_.reduceLeft(_ ++ _))
    assert(await(resultWithSeq) == "FooBar")


    // Other ways that don't quite work: (TODO review)

    import scala.language.reflectiveCalls

    val x = new {
      val f1 = Future { Thread.sleep(100); "Foo" }
      val f2 = Future { Thread.sleep(100); "Bar" }

      def map(f: Int => Any): this.type = {
        this
      }

      final def withFilter(p: Int => Boolean): this.type = {
        this
      }
    }

    for {
      _: Int <- x
    } yield {
      123
    }

  }

}