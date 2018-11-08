package akka_examples

import akka.actor.Actor

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