package scala_examples.styles

import org.scalatest.{FunSuite, Matchers}

class FunSuiteExample extends FunSuite with Matchers {

  test("testAddAll 1") {
    assert(List(10, 1, 2).sum == 13)
  }

  ignore("an ignored test") {
    assert(123 == 321)
  }

  test("testAddAll (this test should fail)") {
    assert(List(10).sum == 13)
  }
}