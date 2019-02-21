package akka_examples

import akka.actor.{Actor, ActorRef, Props}
import akka.testkit.TestProbe
import akka_examples.common.AkkaTest

import scala.concurrent.duration._


class SchedulerExample extends AkkaTest {

  class SchedulePongActor(opponent: ActorRef) extends Actor {

    import context.dispatcher

    context.system.scheduler.scheduleOnce(1.seconds) { self ! "Ping" }

    override def receive: Receive = {

      case "Ping" =>
        opponent ! "PONG"

      case "SLEEP" =>
        Thread.sleep(2000)
      case "Ping-BROKEN" =>
        // This is erroneous because we are accessing actor state (sender)
        // from different execution context (dispatcher)
        // Note: it appears that sender is set to dead letters if Actor not processing message
        context.system.scheduler.scheduleOnce(1.seconds) {
          val sender0 = sender()
          println(s"SENDER: $sender0")
          sender0 ! "PONG-to-sender"
        }
    }
  }

  "Scheduler example" in {
    val probe: TestProbe = TestProbe()
    val probe2: TestProbe = TestProbe()

    val pingPong = system.actorOf(Props(new SchedulePongActor(probe.ref)))

    probe.expectMsg("PONG")

    probe.send(pingPong, "Ping-BROKEN")
    probe2.send(pingPong, "SLEEP")

    // Because of erroneous code, probe2 is one that gets PONG reply
    probe2.expectMsg("PONG-to-sender")
    probe.expectNoMessage(2.second)
  }
}