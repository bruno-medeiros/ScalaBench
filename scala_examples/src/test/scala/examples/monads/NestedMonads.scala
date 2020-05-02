package examples.monads

import scala.concurrent.Future

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.freespec.AnyFreeSpec

//noinspection OptionEqualsSome
class NestedMonads extends AnyFreeSpec with ScalaFutures {

  import scala.concurrent.ExecutionContext.Implicits.global

  def await[T](future: Future[T]): T = {
    future.futureValue
  }

  "Future[Option[]]" in {
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


  // concurrent async monad
  {
    val f1 = Future { Thread.sleep(100); "Foo" }
    val f2 = Future { Thread.sleep(100); "Bar" }

    val resultFt: Future[String] = for {
      v1 <- f1
      v2 <- f2
    } yield v1 ++ v2

    assert(resultFt.futureValue == "FooBar")

    val futureOfSeq: Future[Seq[String]] = Future.sequence(Seq(f1, f2))
    assert(futureOfSeq.map(_.reduceLeft(_ ++ _)).futureValue == "FooBar")

  }

}