package akka_examples.advanced

import akka.actor.{Actor, ActorRef, Props}
import akka.routing.{FromConfig, RoundRobinGroup}
import akka_examples.common.AkkaTest

object RoutingExample {
  class MyWorker extends Actor {
    override def receive: Receive = {
      case "foo" => println("foo")
    }
  }
}

class RoutingExample extends AkkaTest {
  import RoutingExample.MyWorker

  "Example Router with children" in {
    val router1: ActorRef =
      system.actorOf(FromConfig.props(Props[MyWorker]), "router1")
  }

  "Example Router with actor selection" in {
    val paths = List("/user/workers/w1", "/user/workers/w2", "/user/workers/w3")
    val router4: ActorRef =
      system.actorOf(RoundRobinGroup(paths).props(), "router4")
  }
}
