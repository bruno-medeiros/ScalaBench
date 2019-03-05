package examples.matching

import java.net.{MalformedURLException, URL}

import scala.util.control.Exception.catching
import scala.util.control.NonFatal
import scala.util.{Success, Try}

object ExceptionsMatching extends App {

  try {
    new URL("blah")
    assert(false)
  } catch {
    case e: MalformedURLException => assert(true); println(s"Got exception: $e")
    case _: Throwable => assert(false)
  }

  val result: Try[URL] = catching(classOf[MalformedURLException], classOf[NullPointerException])
    .withTry{ new URL("blah") }

  assert(result.failed.get.getMessage.contains("no protocol: blah"))


  // NonFatal
  {
    def matchesNonFatal(exception: Exception) = {
      exception match {
        case NonFatal(_) => true
        case _ => false
      }
    }

    assert(matchesNonFatal(new Exception))
    assert(!matchesNonFatal(new InterruptedException))
  }

  // The issue with Try and monadic laws
  {
    val f: Int => Try[Int] =
      x => throw new Exception("Error:" + x)

    // Doesn't throw, because Try's flatmap handles the "out-of-band" exception throwing
    val tryMonadMapping = Success(123).flatMap(f)
    // It turns it into a Failure
    assert(tryMonadMapping.failed.get.getMessage == "Error:123")
  }
}