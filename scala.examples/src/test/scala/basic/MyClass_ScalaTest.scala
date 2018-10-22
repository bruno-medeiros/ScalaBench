package basic

import org.scalatest.FunSuite

class MyClass_ScalaTest extends FunSuite {

  test("testAddAll 1") {
    val myc = new MyClass(1, 2)
    assert(myc.addAll(10) == 13)
  }

  test("testAddAll 2") {
    val myc = new MyClass(1, 2)
    assert(myc.addAll(20) == 13)
  }
}