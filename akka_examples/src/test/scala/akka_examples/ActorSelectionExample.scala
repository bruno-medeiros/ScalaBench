package akka_examples

import akka.actor.{Actor, Props}
import akka_examples.common.AkkaExample

private class StdoutEchoActor extends Actor {
  override def receive: PartialFunction[Any, Unit] = {
    case message => println(s"$message")
  }
}

private class OtherActor extends Actor {

  override def preStart(): Unit = {
  }

  override def receive: Receive = {
    case "DO" =>
      foo()
      val selection = context.actorSelection("../echo-actor")
      selection ! "hello from other"
  }

  def foo(): Unit = {
    println("in DO")
  }
}

class ActorSelectionExample extends AkkaExample {

  "Test Actor Supervising" in {
    val echoActor = system.actorOf(Props[StdoutEchoActor], "echo-actor")
    echoActor ! "hello"

    // Send message to echo-actor using selection
    val selection = system.actorSelection("user/echo-actor")
    selection ! "hello2"

    // Send message to echo-actor using selection, from an other actor
    system.actorOf(Props[OtherActor]) ! "DO"

//    AkkaApp.awaitEnterPressAndTerminat(system)
  }

}