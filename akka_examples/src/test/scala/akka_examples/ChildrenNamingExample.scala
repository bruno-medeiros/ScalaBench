package akka_examples

import akka.actor.SupervisorStrategy.Stop
import akka.actor.{Actor, ActorLogging, InvalidActorNameException, OneForOneStrategy, Props, Terminated}
import akka.testkit.TestProbe
import akka_examples.common.AkkaExample

object ChildrenNamingExample {

  object Ping
  object Pong
  object SpawnChild
  object SpawnChildExisting
  object StopChild
  object PrintChildren


  class ParentActor extends Actor with ActorLogging {

    override def preStart(): Unit = {
      log.info("PRESTARTING")
      super.preStart()
    }

    override def receive: Receive = {
      case Ping =>
        sender() ! Pong
      case SpawnChild =>
        val _ = context.actorOf(Props[ParentActor], "child")
//        context.watch(child)
        println("Actor children: " + context.children)
      case SpawnChildExisting =>
        try {
          context.actorOf(Props[ParentActor], "child")
          assert(false)
        } catch {
          case e: Throwable => println(">> exception:" + e)
        }

      case StopChild =>
        context.stop(context.child("child").get)
        // context.children is only updated when terminated message received
        assert(context.child("child").isDefined)
        log.info("CHILDREN after stop: " + context.children.toString())

      case Terminated(child) =>
        log.info("TERMINATED: " + child)
        assert(context.children.isEmpty)
        // The actor can now be recreated in this context
        // It is only after receiving terminated that we know context.children is updated

      case PrintChildren =>
        log.info("CHILDREN: " + context.children.toString())
    }
  }
}

class ChildrenNamingExample extends AkkaExample {
  import ChildrenNamingExample._

  "Actor naming in system" in {
    val actor = system.actorOf(Props[ParentActor])
    println(s"Top actor: $actor")
    assert(actor.path.name.startsWith("$"))


    system.actorOf(Props[ParentActor], "foo_parent")

    intercept[InvalidActorNameException] {
      // Creating duplicate will throw name exception
      system.actorOf(Props[ParentActor], "foo_parent")
    }
  }

  "Child naming, after stopped child" in {
    val probe: TestProbe = TestProbe()

    val strategy = OneForOneStrategy(maxNrOfRetries = 10) {
      case e ⇒
        println(s"==> SupervisorStrategy: $e")
        Stop
    }

    val parent = probe.childActorOf(Props[ParentActor], "parent", strategy)

    probe.send(parent, SpawnChild)
    probe.send(parent, SpawnChildExisting)

    // Now stop existing child
    probe.send(parent, StopChild)

    Thread.sleep(400)

    probe.send(parent, PrintChildren)
    // Spawn it again
    probe.send(parent, SpawnChild)

    probe.send(parent, Ping)
    probe.expectMsg(Pong)
  }
}