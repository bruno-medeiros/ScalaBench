package scala_examples.matchers

import org.scalatest.{FunSuite, Matchers}


class MatchersExample extends FunSuite with Matchers {

  val beWithinTolerance = be >= 0 and be <= 10

  test("custom prop? matcher") {
    val result = 5
    result should beWithinTolerance
  }
}
