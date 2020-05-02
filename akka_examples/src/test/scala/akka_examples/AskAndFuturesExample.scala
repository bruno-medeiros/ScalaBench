package akka_examples

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration._

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.Props
import akka.actor.Status.Failure
import akka.event.LoggingReceive
import akka.pattern.ask
import akka.testkit.TestProbe
import akka.util.Timeout
import akka_examples.common.AkkaExample
import org.scalatest.OneInstancePerTest
import org.scalatest.matchers.should.Matchers

class FuturesExampleActor extends Actor with ActorLogging {

  log.info(getClass.getSimpleName + " created")

  override def receive: Receive = LoggingReceive {
    case "question" =>
      log.info(s"Answering question from ${sender()}")
      sender() ! "reply one"
      sender() ! "reply two"
    case msg: String if msg.contains("error") =>
      val errorMsg = s"Error for message `$msg` from ${sender()}"
      log.error(errorMsg)
      if (msg.contains("util.Failure")) {
        sender() ! util.Failure(new Exception(errorMsg))
      } else {
        sender() ! Failure(new Exception(errorMsg))
      }

  }
}

class AskAndFuturesExample extends AkkaExample with OneInstancePerTest with Matchers {

  val questionActor = system.actorOf(Props[FuturesExampleActor])
  val probe = TestProbe()

  implicit val timeout = Timeout(3 seconds)

  "ask from probe actor" in {
    val future = ask(questionActor, "question", probe.ref)
    assertFutureResult(future, "reply one")
  }

  "ask from probe actor - explicit timeout" in {
    import akka.pattern.ask

    val future = questionActor.ask("question")(timeout, probe.ref)
    assertFutureResult(future, "reply one")
  }

  "ask from no actor (creates temp actor)" in {

    val future = ask(questionActor, "question")
    assertFutureResult(future, "reply one")
  }

  "ask and pipeTo pattern" in {

    import akka.pattern.pipe
//    import context.dispatcher
    implicit val ec = scala.concurrent.ExecutionContext.global

    (questionActor ? "question")
      .mapTo[String]
      .map(">>> " ++ _)
      .recover { case ex => Failure(ex) }
      .pipeTo(probe.ref)

    probe.expectMsg(">>> reply one")
  }

  "ask from probe actor - failure" in {
    val future: Future[_] = ask(questionActor, "error reply", probe.ref)
    awaitFailure(future).getMessage.should(include("Error for message `error reply`"))
  }

  "ask from probe actor - util.Failure" in {
    val future: Future[_] = ask(questionActor, "error reply util.Failure", probe.ref)
    // if we don't use Akka's Failure, the scala.util.Failure will count as a Success
    val successResult = awaitResult(future).get.asInstanceOf[util.Failure[Any]]
    successResult.failed.get.getMessage.should(include("Error for message `error reply util.Failure"))
  }

  private def awaitFailure(future: Future[_]) = {
    val result: util.Try[_] = awaitResult(future)
    result.failed.get
  }

  private def awaitResult[T](future: Future[T]) = {
    Await.ready(future, 1 seconds)
    future.value.get
  }
}
