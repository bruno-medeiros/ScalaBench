package akka_examples

import akka.actor.{Actor, InvalidActorNameException, Props}
import akka_examples.common.AkkaTest

object NamingExample {
  class FooActor extends Actor {
    override def receive: Receive = {
      case "Secret" =>
    }
  }
}

class NamingExample extends AkkaTest {
  import NamingExample._

  "Test Actor Naming" in {
    val actor = system.actorOf(Props[FooActor])
    println(s"Actor1: $actor")

    system.actorOf(Props[FooActor], "actor2")

    intercept[InvalidActorNameException] {
      system.actorOf(Props[FooActor], "actor2")
    }

  }
}