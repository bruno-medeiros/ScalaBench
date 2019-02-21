package akka_examples

import akka.actor.{Actor, ActorSystem, Props}

import scala.io.StdIn

object AkkaCounterApp extends App {

  class SimpleCounterActor extends Actor {
    var counter = 0

    override def receive: Receive = {
      case "inc" => counter += 1
      case "dec" => counter -= 1
      case "print" => println(s"Counter: $counter")
    }
  }

  val system = ActorSystem(getClass.getSimpleName.replace("$", ""))

  val counterActor = system.actorOf(Props[SimpleCounterActor])
  counterActor ! "inc"
  counterActor ! "inc"

  counterActor ! "print"

  // ---

  def awaitEnterPressAndTerminate(system: ActorSystem): Unit = {
    println(">>> Press ENTER to exit <<<")
    try StdIn.readLine()
    finally system.terminate()
  }
}