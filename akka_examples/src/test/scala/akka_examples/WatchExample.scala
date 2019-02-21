package akka_examples

import akka.actor.{Actor, ActorRef, PoisonPill, Props, Terminated}
import akka.pattern.gracefulStop
import akka.testkit.TestProbe
import akka_examples.common.AkkaTest

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.language.postfixOps


class WatcherActor(parent: ActorRef) extends Actor {

  override def preStart(): Unit = {
    val child = context.actorOf(Props[ChildActor], "child")
    context.watch(child)
    child ! "Stop"
  }

  override def receive: Receive = {
    case Terminated(subject) =>
      println(s"Watcher received Terminated: $subject" )
      parent ! s"TERMINATED: ${subject.path}"
  }
}

class ChildActor extends Actor {
  override def receive: Receive = {
    case "Stop" => context.stop(self)
  }

  override def postStop(): Unit = {
    println("postStop")
    super.postStop()
  }
}


class WatchExample extends AkkaTest
//  with OneInstancePerTest
{

  "watch example" in {
    val probe: TestProbe = TestProbe()

    system.actorOf(Props(new WatcherActor(probe.ref)), "watcher")

    probe.expectMsg("TERMINATED: akka://AkkaTest/user/watcher/child")
  }

  "graceful stop example" in {
    val probe: TestProbe = TestProbe()

    system.actorOf(Props(new WatcherActor(probe.ref)), "watcher2")

    val eventual: Future[Boolean] = gracefulStop(probe.ref, 1 seconds, PoisonPill)
    val result = Await.result(eventual, 1 seconds)
    assert(result)
  }
}