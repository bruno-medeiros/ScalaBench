package akka_examples

import akka.actor.{Actor, Props, Status}
import akka.event.LoggingReceive
import akka.pattern.ask
import akka.testkit.TestProbe
import akka.util.Timeout
import akka_examples.common.AkkaTest
import org.scalatest.OneInstancePerTest

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.reflect.ClassTag

object AskTellExample {

  class QuestionReplyActor extends Actor {

    println(getClass.getSimpleName + " created")

    override def receive: Receive = LoggingReceive {
      case "question" =>
        println(s"Answering question from ${sender()}")
        sender() ! "reply one"
        sender() ! "reply two"
      case "end_in_failure" =>
        sender() ! Status.Failure(new Exception("My Error"))
    }
  }
}

class AskTellExample extends AkkaTest
  with OneInstancePerTest
{

  import AskTellExample._

  implicit val timeout = Timeout(5 seconds)

  val questionActor = system.actorOf(Props[QuestionReplyActor])
  val probe = TestProbe()

  "send with probe" in {
    probe.send(questionActor, "question")
    probe.expectMsg("reply one")
    probe.expectMsg("reply two")
  }

  "send unknown with probe" in {
    probe.send(questionActor, "UNKNOWN")
    probe.expectNoMessage(500 millis)
  }


  "send from no-actor (deadLetters) " in {
    questionActor ! "question"
  }

  "ask from no actor (creates temp actor)" in {
    implicit val ec = scala.concurrent.ExecutionContext.global

    val rawFuture = ask(questionActor, "question")

    val future = rawFuture.map { x =>
      println("Reply: " + x)
      assert(x === "reply one")
    }
    Await.result(future, 1 seconds)
  }

  "ask from no actor (creates temp actor)" in {
    implicit val ec = scala.concurrent.ExecutionContext.global

    val rawFuture = ask(questionActor, "question")
    val future = rawFuture.map { x =>
      println("Reply: " + x)
      assert(x === "reply one")
    }
    Await.result(future, 1 seconds)
  }
}