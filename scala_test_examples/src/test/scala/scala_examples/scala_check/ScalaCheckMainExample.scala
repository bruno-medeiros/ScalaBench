package scala_examples.scala_check

import org.scalacheck.Prop._
import org.scalacheck.Properties
import org.scalatest.FunSuite
import org.scalatest.prop.Checkers

// This is a main actually
object ScalaCheckMainExample extends Properties("Heap") {

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
