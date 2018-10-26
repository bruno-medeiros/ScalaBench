package basic

import org.scalatest.FunSuite
import org.scalatest.prop.Checkers
import org.scalacheck.Arbitrary._
import org.scalacheck.{Prop, Properties}
import org.scalacheck.Prop._


//@RunWith(classOf[JUnitRunner])
class ScalaCheckFunSuite extends FunSuite with Checkers {

  test("ScalaCheck!") {
    check((a: List[Int], b: List[Int]) => a.size + b.size == (a ::: b).size)
  }

  test("ScalaCheck2 - code from QuickCheck in progfun2 course") {
    class QuickCheckHeap2 extends Properties("Heap") {

      property("startsWith") = forAll { (a: String, b: String) =>
        (a+b).startsWith(a)
      }

      property("concatenate") = forAll { (a: String, b: String) =>
        (a+b).length >= a.length && (a+b).length >= b.length
      }

      property("substring") = forAll { (a: String, b: String, c: String) =>
        (a+b+c).substring(a.length, a.length+b.length) == b
      }
    }

    // Does notncompile, TODO investigate
//    check(new QuickCheckHeap2)
  }
}