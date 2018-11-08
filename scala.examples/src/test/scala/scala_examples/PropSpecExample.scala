package scala_examples

import org.scalatest._
import prop._
import scala.collection.immutable._

class PropSpecExample extends PropSpec with TableDrivenPropertyChecks with Matchers {

  val examples =
    Table(
      "set",
      BitSet.empty,
      HashSet.empty[Int],
      TreeSet.empty[Int]
    )

  property("an empty Set should have size 0") {
    forAll(examples) { set =>
      set.size should be (0)
    }
  }

  property("invoking head on an empty set should produce NoSuchElementException") {
    forAll(examples) { set =>
       a [NoSuchElementException] should be thrownBy { set.head }
    }
  }
}