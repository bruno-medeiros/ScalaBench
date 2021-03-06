package akka_examples.advanced

import akka.actor.Actor
import akka.actor.ActorRef
import akka.routing.RoundRobinGroup
import akka_examples.common.AkkaExample

object RoutingExample {
  class MyWorker extends Actor {
    override def receive: Receive = {
      case "foo" => println("foo")
    }
  }
}

class RoutingExample extends AkkaExample {
  // TODO: proper example

//  import RoutingExample.MyWorker
//
//  "Example Router with children" in {
//    val _: ActorRef =
//      system.actorOf(FromConfig.props(Props[MyWorker]), "router1")
//
//  }

  "Example Router with actor selection" in {
    val paths = List("/user/workers/w1", "/user/workers/w2", "/user/workers/w3")
    val _: ActorRef =
      system.actorOf(RoundRobinGroup(paths).props(), "router4")
  }
}
