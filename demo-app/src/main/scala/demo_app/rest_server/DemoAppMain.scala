package demo_app.rest_server

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext
import scala.util.Failure
import scala.util.Success

object DemoAppMain extends App {

  val host = "0.0.0.0"
  val port = 9000

  implicit val system: ActorSystem = ActorSystem("demo-app")
  implicit val materializer: ActorMaterializer = ActorMaterializer()(system)

  private val server = new DemoAppServer(host, port)

  implicit val executor: ExecutionContext = system.dispatcher

  server.serverBinding.onComplete {
    case Success(_) =>
    case Failure(e) =>
      Console.err.println(s"Server could not start!")
      e.printStackTrace()
      system.terminate()
  }
}