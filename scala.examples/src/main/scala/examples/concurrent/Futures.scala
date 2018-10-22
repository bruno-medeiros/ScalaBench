package examples.concurrent

import java.util.concurrent.TimeUnit

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

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

  val awaitable: Awaitable[_] = answerToLife

  println("Result: " + Await.result(awaitable, Duration(1, TimeUnit.SECONDS)))


  // Promises
  private val p = Promise[String] // defines a promise
  p.future // returns a future that will complete when p.complete() is called
  p.complete(Success("Yes!")) // completes the future
//  p.success("Yes") // successfully completes the promise

}