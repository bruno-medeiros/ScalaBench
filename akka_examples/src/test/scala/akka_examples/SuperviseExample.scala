package akka_examples

import akka.actor.SupervisorStrategy.{Restart, Stop}
import akka.actor.{Actor, ActorKilledException, ActorLogging, Kill, OneForOneStrategy, PoisonPill, Props, SupervisorStrategy}
import akka.testkit.TestProbe
import akka_examples.common.AkkaExample
import org.scalatest.OneInstancePerTest

import scala.concurrent.duration._


class SupervisingActor extends Actor with ActorLogging {
  val child = context.actorOf(Props[SupervisedChild], "child")

  println("Created CHILD")

  override val supervisorStrategy: SupervisorStrategy = {
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case e: IllegalStateException => println(s"==> SupervisorStrategy: ${e.getClass.getSimpleName}"); Restart
      case e: ActorKilledException => println(s"==> SupervisorStrategy: ${e.getClass.getSimpleName}"); Restart
      case e => println(s"==> SupervisorStrategy: ${e.getClass.getSimpleName}"); Stop
    }
  }

  override def receive: Receive = {
    case "errorChild" => child ! "error"
    case "killChild" => child ! Kill

      // Note: this stop/kill unconditionally, they do not cause supervisor strategy to be consulted.
    case "stopChild" => context.stop(child)
    case "poisonChild" => child ! PoisonPill

    case "queryChild" =>
      child.forward("query")
  }
}

class SupervisedChild extends Actor with ActorLogging {
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
      sender() ! isRestarted.toString
  }
}

class SuperviseExample extends AkkaExample
  with OneInstancePerTest
{

  val probe = TestProbe()

  "Test Actor Supervising" in {
    val supervisingActor = system.actorOf(Props[SupervisingActor], "supervising-actor")

    probe.send(supervisingActor, "queryChild")
    probe.expectMsg("false")

    supervisingActor ! "errorChild"
    probe.send(supervisingActor, "queryChild")
    probe.expectMsg("true")
  }

  "Test Actor Supervising - kill child" in {
    val supervisingActor = system.actorOf(Props[SupervisingActor], "supervising-actor-kill")

    supervisingActor ! "killChild"
    probe.send(supervisingActor, "queryChild")
    probe.expectMsg("true")
  }

  "Test Actor Supervising - poison child" in {
    val supervisingActor = system.actorOf(Props[SupervisingActor], "supervising-actor-poison")

    supervisingActor ! "poisonChild"
    probe.send(supervisingActor, "queryChild")
    // If the child is poisoned, it is not restarted
    probe.expectNoMessage(1.second)
  }

  "Test Actor Supervising - stop child" in {
    val supervisingActor = system.actorOf(Props[SupervisingActor], "supervising-actor-stop")

    supervisingActor ! "stopChild"
    probe.send(supervisingActor, "queryChild")
    // If the child is stopped, it is not restarted
    probe.expectNoMessage(1.second)
  }
}