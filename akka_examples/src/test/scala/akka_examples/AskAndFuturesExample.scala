package akka_examples

import akka.actor.Props
import akka.actor.Status.Failure
import akka.pattern.ask
import akka.testkit.TestProbe
import akka.util.Timeout
import akka_examples.common.{AkkaExample, QuestionReplyActor}
import org.scalatest.OneInstancePerTest

import scala.concurrent.duration._
import scala.language.postfixOps


class AskAndFuturesExample extends AkkaExample
  with OneInstancePerTest
{

  val questionActor = system.actorOf(Props[QuestionReplyActor])
  val probe = TestProbe()

  "ask from probe actor" in {
    implicit val timeout = Timeout(3 seconds)

    val future = ask(questionActor, "question", probe.ref)
    assertFutureResult(future, "reply one")
  }

  "ask from probe actor - explicit timeout" in {
    implicit val timeout = Timeout(3 seconds)
    import akka.pattern.ask

    val future = questionActor.ask("question")(timeout, probe.ref)
    assertFutureResult(future, "reply one")
  }

  "ask from no actor (creates temp actor)" in {
    implicit val timeout = Timeout(3 seconds)

    val future = ask(questionActor, "question")
    assertFutureResult(future, "reply one")
  }

  "ask and pipeTo pattern" in {

    import akka.pattern.pipe
//    import context.dispatcher
    implicit val ec = scala.concurrent.ExecutionContext.global
    implicit val timeout = Timeout(3 seconds)

    (questionActor ? "question")
      .mapTo[String]
      .map(">>> " ++ _)
      .recover { case ex => Failure(ex) }
      .pipeTo(probe.ref)

    probe.expectMsg(">>> reply one")
  }

}