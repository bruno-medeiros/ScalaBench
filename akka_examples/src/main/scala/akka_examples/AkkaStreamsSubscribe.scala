package akka_examples

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import akka.{Done, NotUsed}

import scala.concurrent.Future

object AkkaStreamsSubscribe extends App {

  val source: Source[Int, NotUsed] = Source(1 to 50)

  implicit val system = ActorSystem("StreamsHello")
  implicit val materializer = ActorMaterializer()

  val sink: Sink[Int, Future[Done]] = Sink.foreach(i => println(i))
  val done: Future[Done] = source.runWith(sink)

  implicit val ec = system.dispatcher
  done.onComplete(_ => system.terminate())
}

