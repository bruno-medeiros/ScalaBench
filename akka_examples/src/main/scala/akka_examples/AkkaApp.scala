package akka_examples

import akka.actor.ActorSystem

class AkkaApp extends App {
  val system = ActorSystem(getClass.getSimpleName.replace("$", ""))
}