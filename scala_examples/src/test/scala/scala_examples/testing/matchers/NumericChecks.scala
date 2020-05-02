package scala_examples.testing.matchers

import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import scala_examples.testing.FailureExamples

class NumericChecks extends AnyFunSuite with Matchers with TypeCheckedTripleEquals {

  test("double compare - FAIL", FailureExamples) {
    val num = 2.00000001
    assert(num === 2d)
  }

  test("double compare ") {
    val num = 2.00000001
    assert(num === (2d +- 0.001))
  }
}
