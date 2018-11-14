package akka_examples

import akka.actor.{Actor, PoisonPill, Props}
//import akka_examples.SuperviseExample.system


class SupervisingActor extends Actor {
  val child = context.actorOf(Props[SupervisedActor], "supervised-actor")

  override def receive: Receive = {
    case "failChild" ⇒ child ! "fail"
    case "breakChild" ⇒ child ! "UNKNOWN MESSAGE"
    case "stopChild" ⇒ child ! PoisonPill
  }
}

class SupervisedActor extends Actor {
  override def preStart(): Unit = println("supervised actor started")
  override def postStop(): Unit = println("supervised actor stopped")

  override def receive: Receive = {
    case "fail" ⇒
      println("supervised actor fails now")
      throw new Exception("I failed!")
  }
}

class SimpleSuperviseExample extends AkkaTest {

  "Test Actor Supervising" in {
    val supervisingActor = system.actorOf(Props[SupervisingActor], "supervising-actor")
    supervisingActor ! "failChild"
  }
}

class SuperviseAfterStopExample extends AkkaTest {

  "Test Actor Supervising after PoisonPill" in {
    val supervisingActor = system.actorOf(Props[SupervisingActor], "supervising-actor")
    supervisingActor ! "stopChild"
    supervisingActor ! "failChild"
  }

  "Test after unknown message!" in {
    val supervisingActor = system.actorOf(Props[SupervisingActor], "supervising-actor2")
    supervisingActor ! "breakChild"
  }
}