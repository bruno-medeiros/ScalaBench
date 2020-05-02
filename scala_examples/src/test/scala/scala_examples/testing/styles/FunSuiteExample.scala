package scala_examples.testing.styles

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import scala_examples.testing.FailureExamples

class FunSuiteExample extends AnyFunSuite with Matchers {

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
