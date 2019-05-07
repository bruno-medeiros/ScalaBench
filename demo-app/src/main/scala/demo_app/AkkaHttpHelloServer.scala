package demo_app

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.HttpExt
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.HttpResponse
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Flow

import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success


object AkkaHttpHelloServer extends App {

  val host = "0.0.0.0"
  val port = 9000

  implicit val system: ActorSystem = ActorSystem("helloworld")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()(system)

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