package scala_examples

import org.scalatest.{BeforeAndAfterAll, FunSuite, Matchers}

class AsyncExample extends FunSuite with Matchers with BeforeAndAfterAll {

  override protected def beforeAll(): Unit = {
    println("in beforeAll")
  }

  test("Sleep A") {
    println("in SleepA")
    Thread.sleep(2000)
    println("end SleepA")
  }

  test("Sleep B") {
    println("in SleepB")
    Thread.sleep(2000)
    println("end SleepB")
  }
}