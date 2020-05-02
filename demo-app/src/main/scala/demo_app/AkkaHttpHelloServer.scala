package demo_app

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success }

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ HttpRequest, HttpResponse }
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.{ Http, HttpExt }
import akka.stream.scaladsl.Flow

object AkkaHttpHelloServer extends App {

  val host = "0.0.0.0"
  val port = 9000

  implicit val system: ActorSystem = ActorSystem("helloworld")
  implicit val executor: ExecutionContext = system.dispatcher

  def routes: Route = path("hello") {
    get {
      complete("Hello, World!")
    }
  }

  private val httpExt: HttpExt = Http()(system)
  private val handler: Flow[HttpRequest, HttpResponse, NotUsed] = routes

  val serverBinding: Future[Http.ServerBinding] = httpExt.bindAndHandle(handler, host, port)

  serverBinding.onComplete {
    case Success(bound) =>
      println(s"Server online at http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")
    case Failure(e) =>
      Console.err.println(s"Server could not start!")
      e.printStackTrace()
      system.terminate()
  }

}
