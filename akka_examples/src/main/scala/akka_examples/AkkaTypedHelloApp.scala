package akka_examples

import akka.actor
import akka.actor.typed.scaladsl.Behaviors

import akka.actor.typed.scaladsl.adapter._

case class Msg(str: String)

object AkkaTypedHelloApp extends App {

  val root = Behaviors.receive[Msg] { case (ctx, msg) =>
    if (msg.str.contains("ERROR"))
      throw new Exception(msg.str)
    ctx.log.info("Hello " + msg.str)
    Behaviors.empty
  }

  val system = actor.ActorSystem("root")

//  val rootActor = ActorSystem[Msg](root, "HelloWorld")
  val rootActor = system.spawn(root, "HelloWorld")

  rootActor ! Msg("World")

  rootActor ! Msg("World2")
}
