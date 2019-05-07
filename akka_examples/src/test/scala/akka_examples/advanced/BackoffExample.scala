package akka_examples.advanced

import akka.actor.Props
import akka.pattern.BackoffOpts
import akka.pattern.BackoffSupervisor
import akka.testkit.TestActors.EchoActor
import akka_examples.common.AkkaExample

import scala.concurrent.duration.DurationInt


class BackoffExample extends AkkaExample {

  "Backoff Example" in {

    val childProps = Props(classOf[EchoActor])

    val supervisor = BackoffSupervisor.props(
      BackoffOpts.onStop(
        childProps = childProps,
        childName = "myEcho",
        minBackoff = 3.seconds,
        maxBackoff = 30.seconds,
        randomFactor = 0.2, // adds 20% "noise" to vary the intervals slightly
//        maxNrOfRetries = -1
      ))

    val spActor = system.actorOf(supervisor, name = "echoSupervisor")

    spActor ! "hello"
  }
}