package scala_examples

import org.scalatest.{FunSuite, Matchers, OneInstancePerTest}

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