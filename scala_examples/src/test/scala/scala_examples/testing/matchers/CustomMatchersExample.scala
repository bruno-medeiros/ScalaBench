package scala_examples.testing.matchers

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.{ MatchResult, Matcher }

//noinspection ScalaUnnecessaryParentheses
class CustomMatchersExample extends AnyFunSuite with Matchers {

  val beWithinTolerance = be >= 0 and be <= 10

  val beOdd =
    Matcher { (left: Int) =>
      MatchResult(
        left % 2 == 1,
        s"$left was not odd",
        s"$left was odd"
      )
    }

  test("custom prop? matcher") {
    val result = 5
    result should beWithinTolerance
    result should beOdd
  }
}
