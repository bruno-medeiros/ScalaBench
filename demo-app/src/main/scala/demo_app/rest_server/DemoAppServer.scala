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

class DemoAppServer(val host: String, port: Int)(implicit val system: ActorSystem, materializer: ActorMaterializer) {

  implicit val executor: ExecutionContext = system.dispatcher

  val workspaceRegistry: ActorRef[WorkspaceRegistry.Msg] = system.spawn(new WorkspaceRegistry, "registry")

  val handler: Flow[HttpRequest, HttpResponse, NotUsed] = new DemoAppRoutes(system, workspaceRegistry).routes
  val httpExt: HttpExt = Http()(system)

  println(s"Starting server at $host:$port")
  val serverBinding: Future[Http.ServerBinding] = httpExt.bindAndHandle(handler, host, port)


  serverBinding.onComplete {
    case Success(bound) =>
      println(s"Server online at http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")
    case Failure(e) =>
  }
}
