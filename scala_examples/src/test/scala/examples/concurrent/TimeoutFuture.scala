package examples.concurrent

import scala.concurrent._
import scala.concurrent.duration.{ Duration, FiniteDuration }
import scala.util.{ Failure, Try }

// Timed-out future
class TimeoutFuture(timeout: FiniteDuration) extends Future[Nothing] {

  private val p = Promise[Nothing]
  p.future

  val timelimit = timeout.fromNow

  override def onComplete[U](f: Try[Nothing] => U)(implicit executor: ExecutionContext): Unit = {}

  override def isCompleted: Boolean = {
    if (p.isCompleted) {
      true
    } else if (timelimit.isOverdue()) {
      p.tryComplete(Failure(new Exception("Timedout")))
      true
    } else {
      false
    }
  }

  override def value: Option[Try[Nothing]] = {
    if (isCompleted) {
      p.future.value
    } else {
      None
    }
  }

  override def transform[S](f: Try[Nothing] => Try[S])(implicit executor: ExecutionContext): Future[S] = ???

  override def transformWith[S](f: Try[Nothing] => Future[S])(implicit executor: ExecutionContext): Future[S] = ???

  override def ready(atMost: Duration)(implicit permit: CanAwait): this.type = {
    // BUG here
    p.future.ready(atMost)
    this
  }

  override def result(atMost: Duration)(implicit permit: CanAwait): Nothing = {
    if (!isCompleted) {
      //      atMost match {
      //        case atMost: FiniteDuration => {
      //          atMost.fromNow
      //        }
      val atMostMin = atMost.min(timelimit.timeLeft)
      p.future.ready(atMostMin)
      assume(timelimit.isOverdue())
      isCompleted
      assume(p.future.isCompleted)
    }
    p.future.result(atMost)
  }
}
