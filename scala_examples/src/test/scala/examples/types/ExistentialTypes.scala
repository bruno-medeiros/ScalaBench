package examples.types

import scala.collection.mutable.ArrayBuffer
import scala.language.existentials


// Note: these are planned to be removed in Scala 3
object ExistentialTypes extends App {

  import Parameterization.ReadBox

  // Existential types:

  // This is equivalent to Java generics
  def bar1(obj: ArrayBuffer[A] forSome {type A <: Number}): Unit = {
    // Does not compile:
//    obj(1) = new Integer(123)
    // Because we don't know ArrayBuffer[A] exactly, only that A <: Number
  }
  bar1(ArrayBuffer[Integer]())

  def bar2(obj: ArrayBuffer[A forSome {type A <: Number}]): Unit = {
    // obj is equivalent to this:
    val _: ArrayBuffer[Number] = obj
    // As such this works:
    obj(0) = BigInt(123)
    obj(1) = Integer.valueOf(123)
  }
  // This works:
  bar2(ArrayBuffer[Number](1, 2))
  // but this does not compile:
//  bar2(ArrayBuffer[Integer]())


  // Wild card parameter:
  {
    val rb: ReadBox[_] = ReadBox[Int](123)
    val rb2: ReadBox[A forSome {type A}] = rb
    val x: Any = rb.obj
    assert(x.isInstanceOf[Object])
  }

  // TODO: Map[Class[_], String]

}