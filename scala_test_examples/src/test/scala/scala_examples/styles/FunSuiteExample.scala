package scala_examples.styles

import org.scalatest.{FunSuite, Matchers}
import scala_examples.FailureExamples

class FunSuiteExample extends FunSuite with Matchers {

  test("testAddAll 1") {
    assert(List(10, 1, 2).sum == 13)
  }

  ignore("an ignored test") {
    assert(123 == 321)
  }

  test("testAddAll (FAILURE)", FailureExamples) {
    assert(List(10).sum == 13)
  }
}