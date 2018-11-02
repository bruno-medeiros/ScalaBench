package akka_examples

import akka.actor.{Actor, ActorRef, Props, Terminated}
import akka.testkit.TestProbe


class WatcherActor(parent: ActorRef) extends Actor {

  override def preStart(): Unit = {
    val child = context.actorOf(Props[ChildActor], "child")
    context.watch(child)
    child ! "Stop"
  }

  override def receive: Receive = {
    case Terminated(subject) =>
      parent ! s"TERMINATED: ${subject.path}"
  }
}

object WatcherActor {
  def props(parent: ActorRef): Props = Props(new WatcherActor(parent))
}

class ChildActor extends Actor {
  override def receive: Receive = {
    case "Stop" => context.stop(self)
  }
}

class WatchExample extends AkkaTest {

  "watch example" in {
    val probe: TestProbe = TestProbe()

    system.actorOf(WatcherActor.props(probe.ref), "watcher")

    probe.expectMsg("TERMINATED: akka://AkkaTest/user/watcher/child")
  }
}
