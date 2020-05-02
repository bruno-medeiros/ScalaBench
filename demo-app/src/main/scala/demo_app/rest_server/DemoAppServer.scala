package demo_app.rest_server

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success }

import akka.NotUsed
import akka.actor.typed.ActorSystem
import akka.actor.{ ActorSystem => UntypedActorSystem }
import akka.http.scaladsl.model.{ HttpRequest, HttpResponse }
import akka.http.scaladsl.{ Http, HttpExt }
import akka.stream.scaladsl.Flow
import demo_app.workspaces.WorkspaceRegistry

class DemoAppServer(val workspaceRegistrySystem: ActorSystem[WorkspaceRegistry.Msg], val host: String, port: Int)(
  implicit val untypedSystem: UntypedActorSystem
) {

//  implicit val system: ActorSystem[_] = workspaceRegistrySystem
  implicit val executor: ExecutionContext = untypedSystem.dispatcher

  val handler: Flow[HttpRequest, HttpResponse, NotUsed] =
    new DemoAppRoutes(workspaceRegistrySystem) (workspaceRegistrySystem).routes
  val httpExt: HttpExt = Http()(untypedSystem)

  println(s"Starting server at $host:$port")
  val serverBinding: Future[Http.ServerBinding] = httpExt.bindAndHandle(handler, host, port)

  serverBinding.onComplete {
    case Success(bound) =>
      println(s"Server online at http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")
    case Failure(e) =>
  }
}
