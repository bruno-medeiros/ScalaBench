package scala_examples.testing

import org.scalatest.FunSuite
import org.scalatest.Matchers
import org.scalatest.OneInstancePerTest

class PrintDelayExample extends FunSuite
  with Matchers
  with OneInstancePerTest
{

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