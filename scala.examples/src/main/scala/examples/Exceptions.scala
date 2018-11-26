package examples

import scala.util.Try
import scala.util.control.Exception._
import java.net._

object Exceptions extends App {

  val result: Try[URL] = catching(classOf[MalformedURLException], classOf[NullPointerException])
    .withTry{ new URL("blah") }

  assert(result.failed.get.getMessage.contains("no protocol: blah"))

}