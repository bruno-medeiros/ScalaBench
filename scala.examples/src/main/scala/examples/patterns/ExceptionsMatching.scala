package examples.patterns

import scala.util.control.NonFatal
import scala.util.{Success, Try}

object ExceptionsMatching extends App {

  try {
    throw new RuntimeException("Boom")
  } catch {
    case e : RuntimeException => println(s"Got RuntimeException! ${e.getMessage}")
    case NonFatal(e) => println(s"Got NonFatal $e")
  }

  // The issue with Try and monadic laws
  {
    val f: Int => Try[Int] = x => throw new Exception("Error:" + x)

    // Succeeds because Try's flatmap handles the "out-of-band" exception throwing
    println("Unit . flatmap: " + Success(123).flatMap(f))
    // This fails tho, even if normally it would be equivalent:
    println(f(123))

  }
}