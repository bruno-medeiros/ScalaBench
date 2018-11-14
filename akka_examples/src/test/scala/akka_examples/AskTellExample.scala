package akka_examples

import akka.actor.{Actor, Props}
import akka.pattern.ask
import akka.testkit.TestProbe
import akka.util.Timeout
import org.scalatest.OneInstancePerTest

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

object AskTellExample {

  class QuestionReplyActor extends Actor {

    println(getClass.getSimpleName + " created")

    override def receive: Receive = {
      case "question" =>
        println(s"Answering question from ${sender()}")
        sender() ! "reply one"
        sender() ! "reply two"
    }
  }
}

class AskTellExample extends AkkaAsyncTest
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

    succeed
  }

  "send unknown with probe" in {
    probe.send(questionActor, "UNKNOWN")
    probe.expectNoMessage(500 millis)

    succeed
  }


  "send from no-actor (deadLetters) " in {
    questionActor ! "question"
    succeed
  }

  "ask from no actor (creates temp actor)" in {
    implicit val ec = scala.concurrent.ExecutionContext.global

    val rawFuture = ask(questionActor, "question")

    val future = rawFuture.map(x => println("Reply: " + x))
    Await.result(future, 1 seconds)
    succeed
  }

  "ask from no actor (creates temp actor) - ASYNC" in {
    val rawFuture = ask(questionActor, "question")

    rawFuture.map{ x =>
      println("Reply: " + x)
      assert(x == "reply one")
    }
  }
}