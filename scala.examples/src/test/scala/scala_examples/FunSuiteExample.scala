package scala_examples

import basic.MyClass
import org.scalatest.{FunSuite, Matchers}

class FunSuiteExample extends FunSuite with Matchers {

  test("testAddAll 1") {
    val myc = new MyClass(1, 2)
    assert(myc.addAll(10) == 13)
  }

  test("testAddAll (this test should fail)") {
    val myc = new MyClass(1, 2)
    assert(myc.addAll(20) == 13)
  }
}