package akka_examples.typed

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior}

object AkkaTyppedHello extends App {

  val greeter: Behavior[String] = Behaviors.receive[String] { (ctx, msg) =>
    msg match {
      case whom =>
      println(s"Hello `$whom`, I am `${ctx.self}`")
      Behaviors.stopped
    }
  }

  val system: ActorSystem[String] = ActorSystem(greeter, "helloworld")

  system ! "World"

}