package demo_app.rest_server

import akka.NotUsed
import akka.actor.ActorSystem
import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.adapter._
import akka.http.scaladsl.Http
import akka.http.scaladsl.HttpExt
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.HttpResponse
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Flow
import demo_app.workspaces.WorkspaceRegistry

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
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