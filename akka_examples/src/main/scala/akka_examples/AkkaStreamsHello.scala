package akka_examples

import java.nio.file.Paths

import akka.actor.ActorSystem
//import akka.stream.scaladsl.
import akka.stream.scaladsl.FileIO
import akka.stream.scaladsl.Flow
import akka.stream.scaladsl.Keep
import akka.stream.scaladsl.Sink
import akka.stream.scaladsl.Source
import akka.stream.ActorMaterializer
import akka.stream.IOResult
import akka.util.ByteString
import akka.Done
import akka.NotUsed

import scala.concurrent.Future

object AkkaStreamsHello extends App {

  val source: Source[Int, NotUsed] = Source(1 to 50)

  //#run-source

  implicit val system = ActorSystem("StreamsHello")
  implicit val materializer = ActorMaterializer()

  val done: Future[Done] = source.runForeach(i => println(i))(materializer)

  implicit val ec = system.dispatcher
  done.onComplete(_ => system.terminate())
}

object AkkaStreamsFactorialApp extends App {

  val source: Source[Int, NotUsed] = Source(1 to 50)

  //#run-source

  implicit val system = ActorSystem("StreamsHello")
  implicit val materializer = ActorMaterializer()

  val printSourceFt: Future[Done] = source.runForeach(i => println(i))(materializer)

  val factorials = source.scan(BigInt(1))((acc, next) => acc * next)

  val factorialsFileWriteFt: Future[IOResult] =
    factorials
      .map(num => ByteString(s"$num\n"))
      .runWith(FileIO.toPath(Paths.get("factorials.txt")))

  implicit val ec = system.dispatcher
  printSourceFt.zipWith(factorialsFileWriteFt)((_, _)).onComplete(_ => system.terminate())

}


object Misc {
  def lineSink(filename: String): Sink[String, Future[IOResult]] = {

    val sink: Sink[ByteString, Future[IOResult]] = FileIO.toPath(Paths.get(filename))
    val flow: Flow[String, String, NotUsed] = Flow[String]
    flow
      .map(s => ByteString(s + "\n"))
      .toMat(sink)(Keep.right)
  }
}