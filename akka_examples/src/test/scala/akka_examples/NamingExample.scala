package akka_examples

import akka.actor.{Actor, Props}

class FooActor extends Actor {
  override def receive: Receive = {
    case "Secret" =>
  }
}

class NamingExample extends AkkaTest {

  "Test Actor Naming" in {
    val actor = system.actorOf(Props[FooActor])
    println(s"Actor1: $actor")

    val actor2 = system.actorOf(Props[FooActor], "actor2")
    val actor2b = system.actorOf(Props[FooActor], "actor2")
  }
}