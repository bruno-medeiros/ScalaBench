package scala_examples.async

import java.io.File
import org.scalatest._
import scala.concurrent.Future

class AsyncExample2 extends AsyncFlatSpec {

  override def withFixture(test: NoArgAsyncTest) = {

    super.withFixture(test) onFailedThen { _ =>
      val currDir = new File(".")
      val fileNames = currDir.list()
      info("Dir snapshot: " + fileNames.mkString(", "))
    }
  }

  def addSoon(addends: Int*): Future[Int] = Future { addends.sum }

  "This test" should "succeed" in {
    addSoon(1, 1) map { sum => assert(sum == 2) }
  }

  it should "fail" in {
    addSoon(1, 1) map { sum => assert(sum == 3) }
  }
}