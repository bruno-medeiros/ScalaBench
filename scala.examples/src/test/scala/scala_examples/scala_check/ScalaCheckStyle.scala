package scala_examples.scala_check

import org.scalacheck.Prop.{BooleanOperators, forAll, _}
import org.scalatest.FunSuite
import org.scalatest.prop.Checkers

/**
  * Checkers is ScalaCheck style property-based testing
  * (the property function returns a boolean)
  */
class ScalaCheckStyle extends FunSuite with Checkers {

  test("ScalaCheck style") {
    check((a: List[Int], b: List[Int]) => a.size + b.size == a.size)
  }

  test("ScalaCheck style with implication") {

    // This doesn't compile in IntelliJ, but is valid code.
//    val propMakeList = forAll { n: Int =>
//      (n >= 0 && n < 10000): ==> (List.fill(n)("").length == n)
//    }

  }
}