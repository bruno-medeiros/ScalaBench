package akka_examples

import akka.actor.SupervisorStrategy.{Restart, Stop}
import akka.actor.{Actor, ActorLogging, Kill, OneForOneStrategy, PoisonPill, Props, SupervisorStrategy}
import akka.testkit.TestProbe

import scala.concurrent.duration._
import scala.language.postfixOps


class SupervisingActor extends Actor with ActorLogging {
  val child = context.actorOf(Props[SupervisedActor], "child")
  context.actorSelection("!")
  println("Created CHILD")

  override val supervisorStrategy: SupervisorStrategy = {
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: IllegalStateException ⇒
        Restart
      case _: Exception ⇒
        Stop
    }
  }

  override def receive: Receive = {
    case "errorChild" ⇒ child ! "error"
    case "stopChild" ⇒ context stop child
    case "poisonChild" ⇒ child ! PoisonPill
    case "killChild" ⇒ child ! Kill

    case "queryChild" ⇒
      child.forward("query")
  }
}

class SupervisedActor extends Actor with ActorLogging {
  var isRestarted = false

  override def preStart(): Unit = println("supervised actor started")
  override def postStop(): Unit = println("supervised actor stopped")

  override def postRestart(reason: Throwable): Unit = {
    println("child postRestart")
    isRestarted = true
    super.postRestart(reason)
  }


  override def receive: Receive = {
    case "error" =>
      println("supervised actor fails now")
      throw new IllegalStateException("I failed!")
    case "query" =>
      println(s"queried... isRestarted = $isRestarted")
      sender() ! isRestarted
  }
}

class SuperviseExample extends AkkaTest {

  "Test Actor Supervising" in {
    val supervisingActor = system.actorOf(Props[SupervisingActor], "supervising-actor")
    val probe = TestProbe()

    probe.send(supervisingActor, "queryChild")
    probe.expectMsg(false)

    supervisingActor ! "errorChild"
    probe.send(supervisingActor, "queryChild")
    probe.expectMsg(true)
  }

  "Test Actor Supervising - poison" in {
    val supervisingActor = system.actorOf(Props[SupervisingActor], "supervising-actor-poison")
    val probe = TestProbe()

    supervisingActor ! "poisonChild"
    probe.send(supervisingActor, "queryChild")
    // If the child is stopped, it is not restarted
    probe.expectNoMessage()
  }
}