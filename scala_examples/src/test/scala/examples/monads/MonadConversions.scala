package examples.monads

import scala.annotation.nowarn
import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success, Try }

import cats.{ Monad, MonadError }
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.freespec.AnyFreeSpec

//noinspection ScalaUnusedSymbol
@nowarn("cat=unused")
class MonadConversions extends AnyFreeSpec with ScalaFutures {

  "Future and throws " in {
    implicit val ec = ExecutionContext.global

    Future
      .successful(123)
      .map(throwException)
      .failed
      .futureValue
      .getMessage
      .assertEqual("Failed")

    Future
      .failed(new Exception("xxx"))
      .recover { case e => throwException(111) }
      .failed
      .futureValue
      .getMessage
      .assertEqual("Failed")

    assertThrows[Exception] {
      // Fails because it's the first for clause
      for {
        _ <- Future.successful(throwException(123))
      } yield ()
    }

    {
      for {
        _ <- Future.successful(123)
        // if the exception is throw in second clause tho, it works:
        _ <- Future.successful(throwException(123))
      } yield ()
    }
  }

  def throwException(num: Int): String = {
    throw new Exception("Failed")
  }
  implicit class AssertEqualsOps[T](obj: T) {
    def assertEqual(other: T) = assert(obj == other)
  }

  "Monad and MonadError" in {
    implicit val ec = ExecutionContext.global
    val fme: MonadError[Future, Throwable] = cats.implicits.catsStdInstancesForFuture

    val result: Future[String] = fme.unit
      .map[String](e => throw new Exception("Failed Future"))
    result.failed.futureValue.getMessage.assertEqual("Failed Future")

    val optMonad: Monad[Option] = cats.implicits.catsStdInstancesForOption

    // Option is not a MonadError, and so cannot handle exceptions
    assertThrows[Exception] {
      val result: Option[String] = optMonad.unit
        .map[String](e => throw new Exception("Failed Future"))
    }
  }

  // ----------------------

  "Convert one Monad to another " in {

    def convert(option: Option[Int]): Try[String] = {
      option match {
        case Some(value) => Success(value.toString)
        case None        => Failure(new Exception("value is missing!"))
      }
    }

    def convert2(option: Option[Int]): Try[String] = {
      option
        .map(value => Success(value.toString))
        .getOrElse(Failure(new Exception("value is missing!")))
    }
  }

  "Convert Monad to another - with MonadError" in {

    def convert[F[_]](option: Option[Int])(implicit me: MonadError[F, Throwable]): F[String] = {
      option
        .map(value => me.pure(value.toString))
        .getOrElse(me.raiseError(new Exception("value is missing!")))
    }
  }

}
