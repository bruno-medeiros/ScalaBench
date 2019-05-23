package cats_examples

import cats.effect.ContextShift
import cats.effect.IO
import cats.implicits._

import scala.concurrent.ExecutionContext

object CatsShiftExample extends App {

  // Needed for IO.start to do a logical thread fork
  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  {
    val io = IO[Int] {
      println(s"Basic IO 1 at ${Thread.currentThread()}")
      123
    }
    assert(io.unsafeRunSync() == 123)
  }

  {
    val io =  IO.shift *> IO[Int] {
      println(s"Basic IO 2 at ${Thread.currentThread()}")
      456
    }
    assert(io.unsafeRunSync() == 456)
  }

  println("---")

  {
    // What???
    val io = IO.suspend(IO{
      println(s"Suspend at ${Thread.currentThread()}")
      "blah"
    }).unsafeRunSync()
    assert(io == "blah")
  }

  // ???
  IO.pure(10).map(_ + 2)
  IO{ 10 + 2 }
  IO.suspend(IO{ 10 + 2 })

}
