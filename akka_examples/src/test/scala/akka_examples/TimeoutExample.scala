package akka_examples

import akka.actor.{Actor, Props}
import akka.testkit.TestProbe
import akka_examples.TimeoutExample.Timeout.StartSelfShutdown

import scala.concurrent.duration.DurationInt
//import scala.language.postfixOps

object TimeoutExample {

  object Timeout {
    case class StartSelfShutdown()
    case class Reply(msg: String)
  }

  class Timeout extends Actor {

    import context.dispatcher

    override def receive: Receive = {
      case StartSelfShutdown() =>
        println("Self Shutdown")
        sender() ! "blah"
        context.system.scheduler.scheduleOnce(1.seconds, sender(), "TheEnd")
    }
  }
}

class TimeoutExample extends AkkaTest {
  import TimeoutExample._

  "Timeout actor should TheEnd" in {
    val probe = TestProbe()
    val actor = system.actorOf(Props[Timeout])

    actor.tell(Timeout.StartSelfShutdown(), probe.ref)

    probe.expectMsg("blah")
    probe.expectMsg("TheEnd")
  }

  "Timeout actor should TheEnd - awaitAssert" in {
    val probe = TestProbe()
    val actor = system.actorOf(Props[Timeout])

    actor.tell(Timeout.StartSelfShutdown(), probe.ref)

    probe.awaitAssert {
      val response = probe.expectMsgType[String]
      response should ===("TheEnd")
    }
  }

  "This test will fail due " in {
    val probe = TestProbe()

    probe.expectMsg("Blah")

//    probe.awaitAssert()
  }

}
