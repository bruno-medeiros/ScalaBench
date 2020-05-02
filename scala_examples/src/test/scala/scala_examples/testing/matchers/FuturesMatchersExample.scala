package scala_examples.testing.matchers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class FuturesMatchersExample extends AnyFunSuite with ScalaFutures with Matchers {

  test("future") {
    val f = Future { (42, "abc") }

    f.futureValue shouldEqual (42, "abc")
  }

  test("future - exception") {
    val f = Future { throw new IllegalArgumentException }

    f.failed.futureValue.getClass shouldBe classOf[IllegalArgumentException]
  }

}
