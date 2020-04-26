package scala_examples.testing

import org.scalatest.OneInstancePerTest
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should

class PrintDelayExample extends AnyFunSuite with should.Matchers with OneInstancePerTest {

  test("Sleep A") {
    println("in SleepA")
//    Thread.sleep(2000)
    println("end SleepA")
    Console.flush()
  }

  test("Sleep B") {
    println("in SleepB !!!")
    Thread.sleep(2000)
    println("end SleepB")
  }
}
