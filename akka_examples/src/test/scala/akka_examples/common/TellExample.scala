package akka_examples.common

import akka.actor.Status.Failure
import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.event.LoggingReceive
import akka.testkit.TestProbe
import org.scalatest.OneInstancePerTest

import scala.concurrent.duration._

class QuestionReplyActor extends Actor with ActorLogging {

  log.info(getClass.getSimpleName + " created")

  override def receive: Receive = LoggingReceive {
    case "question" =>
      log.info(s"Answering question from ${sender()}")
      sender() ! "reply one"
      sender() ! "reply two"
    case "UNKNOWN" =>
      log.warning(s"UNKNOWN from ${sender()}")
      // Do nothing
    case str: String =>
      val errorMsg = s"Unknown message `$str` from ${sender()}"
      log.error(errorMsg)
      sender() ! Failure(new Exception(errorMsg))

  }
}

class TellExample extends AkkaExample
  with OneInstancePerTest
{

  val questionActor = system.actorOf(Props[QuestionReplyActor])
  val probe = TestProbe()

  "tell" in {
    implicit val sender = probe.ref

    questionActor ! "question"
    probe.expectMsg("reply one")
    probe.expectMsg("reply two")

    questionActor ! "UNKNOWN"
    probe.expectNoMessage(500 millis)
  }

  "tell from no-actor (reply goes to deadLetters)" in {
    questionActor ! "question"
    // reply will go to deadLetters
    probe.expectNoMessage(200 millis)
  }

  "tell with send API" in {
    probe.send(questionActor, "question")
    probe.expectMsg("reply one")
    probe.expectMsg("reply two")

    probe.send(questionActor, "UNKNOWN")
    probe.expectNoMessage(500 millis)
  }

  "forward example" in {
    val forwarder = system.actorOf(Props(new ForwardingActor(questionActor)))

    probe.send(forwarder, "question")
    probe.expectMsg("reply one")
    probe.expectMsg("reply two")
  }

  class ForwardingActor(target: ActorRef) extends Actor with ActorLogging {

    override def receive: Receive = LoggingReceive {
      case msg =>
        log.info(s"Forwarding $msg to $target")

        target forward msg
    }
  }

}