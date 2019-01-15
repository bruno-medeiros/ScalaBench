package scala_examples.matchers

import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.{FunSuite, Matchers}

class NumericChecks extends FunSuite with Matchers with TypeCheckedTripleEquals {

  test("double compare - FAIL") {
    val num = 2.00000001
    assert(num === 2d)
  }

  test("double compare ") {
    val num = 2.00000001
    assert(num === (2d +- 0.001))
  }
}