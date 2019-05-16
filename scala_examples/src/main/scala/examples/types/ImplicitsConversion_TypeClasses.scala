package examples.types

import org.scalatest.Assertions

import scala.language.implicitConversions

object ImplicitsConversion_TypeClasses extends App with Assertions {

  // This is called a type class
  implicit class StringOps(num: Int) {
    def extensionMethod = s"ExtendedOp! for $num"
  }

  // implicit converstion to StringOps
  assert(123.extensionMethod === "ExtendedOp! for 123")
  // Note, what is SEARCHED for is not StringOps type,
  // but rather, any implicit with extensionMetho

}