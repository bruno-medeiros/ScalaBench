package scala_examples.matchers

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FunSuite, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class FuturesMatchersExample extends FunSuite
  with ScalaFutures
  with Matchers
{

  test("future") {
    val f = Future { (42, "abc") }

    f.futureValue shouldEqual (42, "abc")
  }

  test("future - exception") {
    val f = Future { throw new IllegalArgumentException }

    f.failed.futureValue.getClass shouldBe classOf[IllegalArgumentException]
  }

}