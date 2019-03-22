package examples.concurrent

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

object Futures extends App {

  // The function to be run asynchronously
  val answerToLife: Future[Int] = Future {
    42
  }


  // These are various callback functions that can be defined
  answerToLife onComplete {
    case Success(result) => result
    case Failure(t) => println("An error has occurred: " + t.getMessage)
  }

  answerToLife.map(num => num + 100)


  // Await future result (blocking)
  assert(42 == Await.result(answerToLife, 1 seconds))


  private val nfeFuture = Future {
    throw new NumberFormatException
  }
  println("Result2: " + Try{ Await.result(nfeFuture, 1 seconds) })


  // Throwing InterruptedException in Future - it doesn't complete the future!
  // This is consequenece of InterruptedException considered Fatal as in NonFatal.unapply
  {
    val throwingInt = Future {
      throw new InterruptedException
    }
    try Await.result(throwingInt, 3 seconds)
    catch {
      case _ : InterruptedException => //ok
      case _ : Throwable => assert(true)
    }
  }


  // completed futures
  {
    val f1 = Future.failed(new NoSuchElementException)
    assert(Await.result(f1.failed, 1 seconds).isInstanceOf[NoSuchElementException])
    assert(Await.result(f1.recover { case _: NoSuchElementException => 123 }, 1 seconds) == 123)
  }
  {
    // This will box InterruptedException in an ExecutionException ಠ_ಠ'
    val f2 = Future.failed(new InterruptedException)
    assert(Await.result(f2.recover { case _: ExecutionException => 123 }, 1 seconds) == 123)
    assert(Await.result(f2.failed, 1 seconds).isInstanceOf[ExecutionException])
    assert(Await.ready(f2, 1 seconds).value.get.failed.get.isInstanceOf[ExecutionException])
    // Should be fixed in future:
    // https://github.com/scala/bug/issues/9554
  }


  // blocking
  {
    val blockFuture = Future {
      blocking {
        Thread.sleep(1000)
      }
    }
    Await.result(blockFuture, 3 seconds)
  }

}