package cats_examples

import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext

import cats.effect.{ ContextShift, Fiber, IO }
import cats.implicits._
import org.scalatest.FreeSpec

// These are kinda redundant now since we are testing just Circe now...
class CatsEffectExamples extends FreeSpec {

  val throwingTask = IO.async[String](
    _ => { throw new Exception("thrown") }
  )

  s"thrown exception" ignore {
    throwingTask.unsafeRunSync()
  }

  "thrown exception - async" in {
    throwingTask.unsafeRunAsync(cb => {
      println(cb)
    })
    Thread.sleep(500)
  }

  val myEffect: IO[Int] = IO.delay {
    println("My effect")
    123
  }

  "IO effect - bracket" in {

    val eff2 = myEffect.bracket { n =>
      println("Bracket use")

      IO {
        println("Bracket use")
        n.toString + "---"
      } *> IO.raiseError(new Exception("Error!"))
    } { _ =>
      IO {
        println("Bracket release")
      }
    }

    try {
      eff2.unsafeRunSync()
    } catch {
      case _: Throwable =>
    }
  }

  def sampleTask(id: String) = IO {
    print(s"SampleTask[$id]  ")

    "1234".foreach { ch =>
      print(s"$ch")
      Thread.sleep(100)
    }
    println(s"  END[$id]")
  }

  "concurrent example" in {

    // Single thread executor will make tasks not-interleaved
    runFourSampleTasks(IO.contextShift(ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor())))
    println("----------------")

    // Multiple threads executor will interleave threads. How to block then?
    runFourSampleTasks(IO.contextShift(ExecutionContext.global))
  }

  private def runFourSampleTasks(cs: ContextShift[IO]) = {
    implicit val _cs = cs

    val tasks = List(
      sampleTask("A"),
      sampleTask("B"),
      sampleTask("C"),
      sampleTask("D"),
    )
    val fibersIO: IO[List[Fiber[IO, Unit]]] = tasks
      .map(task => {
        val start: IO[Fiber[IO, Unit]] = task.start
        println("Task started")
        start
      })
      .sequence

    val joinTasks0: IO[IO[List[Unit]]] = fibersIO
      .map(fibers => {
        fibers.map {
          _.join
        }.sequence
      })

    val joinTasks: IO[List[Unit]] = joinTasks0.flatten

    joinTasks.unsafeRunSync()
  }

}
