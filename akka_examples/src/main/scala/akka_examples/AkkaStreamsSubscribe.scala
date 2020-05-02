package akka_examples

import scala.concurrent.Future

import akka.actor.ActorSystem
import akka.stream.scaladsl.{ Sink, Source }
import akka.{ Done, NotUsed }

object AkkaStreamsSubscribe extends App {

  val source: Source[Int, NotUsed] = Source(1 to 50)

  implicit val system = ActorSystem("StreamsHello")

  val sink: Sink[Int, Future[Done]] = Sink.foreach(i => println(i))
  val done: Future[Done] = source.runWith(sink)

  implicit val ec = system.dispatcher
  done.onComplete(_ => system.terminate())
}
