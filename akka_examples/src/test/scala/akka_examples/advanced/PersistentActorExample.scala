package akka_examples.advanced

import akka.actor.Props
import akka.persistence.PersistentActor
import akka.testkit.TestProbe
import akka_examples.common.AkkaTest

// TODO: REVIEW

object CounterActor {

  sealed trait Op

  case class Increment(amount: Int) extends Op
  case class Decrement(amount: Int) extends Op

  case class OpMsg(op: Op)
}

class CounterActor extends PersistentActor {
  import CounterActor._

  var counter: Int = 0

  println("CounterActor create")

  override def persistenceId: String = "counter-actor"

  override def receiveRecover: Receive = {
    case op: Op =>
      println(s"Recovering: $op")
      updateState(op)
//    case SnapshotOffer(_, _) => ???
  }

  override def receiveCommand: Receive = {
    ???
  }

  private def updateState(op: Op): Unit = {
    println(s"updating state: $op")
    op match {
      case Increment(amount) =>
        counter += amount
      case Decrement(amount) =>
        counter -= amount
    }
  }
}


class PersistentActorExample extends AkkaTest {
  import CounterActor._

  "Counter Actor" in {
    val probe = TestProbe()
    val counterActor = system.actorOf(Props[CounterActor], "counter-actor-p")
    probe.send(counterActor, Increment(10))

    probe.send(counterActor, OpMsg(Increment(10)))

    //    counterActor ! Increment(10)
//    counterActor ! Increment(20)
  }
}