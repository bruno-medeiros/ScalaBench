package demo_app.rest_server

import scala.concurrent.ExecutionContext
import scala.util.{ Failure, Success }

import akka.actor.typed.ActorSystem
import akka.actor.{ ActorSystem => UntypedActorSystem }
import demo_app.workspaces.WorkspaceRegistry

object DemoAppMain extends App {

  val host = "0.0.0.0"
  val port = 9000

  implicit val system: UntypedActorSystem = UntypedActorSystem("demo-app")

  implicit val workspaceRegistrySystem: ActorSystem[WorkspaceRegistry.Msg] =
    ActorSystem[WorkspaceRegistry.Msg](new WorkspaceRegistry(), "iot-system")

  private val server = new DemoAppServer(workspaceRegistrySystem, host, port)

  implicit val executor: ExecutionContext = system.dispatcher

  server.serverBinding.onComplete {
    case Success(_) =>
    case Failure(e) =>
      Console.err.println(s"Server could not start!")
      e.printStackTrace()
      system.terminate()
  }
}
