package examples

import scala.util.Try
import scala.util.control.Exception._
import java.net._

object ExceptionsAndTryCatch extends App {

  try {
    new URL("blah")
    assert(false)
  } catch {
    case _: MalformedURLException => assert(true)
    case _: Throwable => assert(false)
  }

  val result: Try[URL] = catching(classOf[MalformedURLException], classOf[NullPointerException])
    .withTry{ new URL("blah") }

  assert(result.failed.get.getMessage.contains("no protocol: blah"))

}