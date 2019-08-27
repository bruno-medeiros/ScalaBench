package examples.concurrent

import scala.concurrent._
import scala.concurrent.duration._

object Promises extends App {

  // Promises
  val p = Promise[String] // defines a promise
  p.future // returns a future that will complete when p.complete() is called
  p.failure(new Exception("Fail")) // completes the future

  Await.result(p.future, 1.seconds)

}