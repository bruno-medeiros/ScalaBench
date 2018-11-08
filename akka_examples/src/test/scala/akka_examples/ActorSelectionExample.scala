package akka_examples

import akka.actor.{Actor, Props}

private class StdoutEchoActor extends Actor {
  override def receive = {
    case message ⇒ println(s"$message")
  }
}

class ActorSelectionExample extends AkkaTest {

  "Test Actor Supervising" in {
    val echoActor = system.actorOf(Props[StdoutEchoActor], "echo-actor")
    echoActor ! "hello"

    system.actorOf(Props[OtherActor]) ! "DO"

    val selection = system.actorSelection("user/echo-actor")
    selection ! "hello2"


    ActorSelectionExample.pausePoint()
  }

}

object ActorSelectionExample {

  def pausePoint() = {
    println("---- End of Test2 ----")
  }
}