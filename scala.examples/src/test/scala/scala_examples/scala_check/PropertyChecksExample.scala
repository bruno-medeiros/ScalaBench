package scala_examples.scala_check

import org.scalacheck.Gen
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.prop.PropertyChecks

/**
  * GeneratorDrivenPropertyChecks is ScalaTest style property-based testing
  */
class PropertyChecksExample extends FunSuite with PropertyChecks with Matchers {

  test("startsWith") {
    forAll { (a: String, b: String) =>
      assert((a + b).startsWith(a))
    }
  }

  test("startsWith - FAILURE") {
    forAll { (a: String, b: String) =>
      (a + b) should startWith("a")
    }
  }

  test("isEmpty") {
    forAll{ a: String => {
      whenever (a.length > 0) {
        assert(!a.isEmpty)
      }
    }}
  }

  test("concatenate") {
    forAll { (a: String, b: String) =>
      assert((a + b).length >= a.length && (a + b).length >= b.length)
    }
  }

  val myGen = for {
    n <- Gen.choose(10,20)
    m <- Gen.choose(2*n, 500)
  } yield (n,m)

  test("with generators") {
    forAll (myGen) {
      a: (Int, Int) => a._1 should be < a._2
    }
  }

  test("with generators - FAIL") {
    forAll (myGen) {
      a: (Int, Int) => a._1 should be > a._2
    }
  }

  val vowel = Gen.oneOf('A', 'E', 'I', 'O', 'U', 'Y')

  val smallEvenInteger = Gen.choose(0,200) suchThat (_ % 2 == 0)
}