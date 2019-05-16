package examples.types

import org.scalatest.Assertions

import scala.annotation.implicitNotFound
import scala.language.implicitConversions


object ImplicitsExamples extends App with Assertions {

  class Xpto { def foo(string: String): Unit = println(s"Myclass + $string") }

  // Implicit value:
  implicit val imp: Xpto = new Xpto

  def withImplicit(name: String) (implicit myClass: Xpto): Unit = {
    myClass.foo(name)
  }

  // Call function with implicit parameter
  withImplicit("Blah")

  // Request implict type directly
  implicitly[Xpto].foo("Hello")

  {
    @implicitNotFound(msg = "oh crap!!! ${T1} and ${T2}")
    implicit class SomeOps[T1, T2 <: Exception](string: String) {}

    // Uncomment to show errorMessage:
    //implicitly[SomeOps[String, Exception]]
  }


  trait Ziltoid[T] {
    def insult(t: T): String = { t.toString + " sucks!"}
  }
  object Ziltoid {
    implicit val xptoZiltoid = new Ziltoid[Xpto] { }
  }

  case class Bar (name: String) { }
  //noinspection ScalaUnusedSymbol
  object Bar  {
    implicit val ziltoid = new Ziltoid[Bar] { }
  }
  assert(Bar("xxx").toString === "Bar(xxx)")

  {
    // implicit type SEARCHED in companion object of Ziltoid
    implicitly[Ziltoid[Xpto]]

    // implicit type SEARCHED in companion object of Bar (Ziltoid's T)
    implicitly[Ziltoid[Bar]]


    def foo0[T](tee: T)(implicit ziltoid: Ziltoid[T]): String = {
      ziltoid.insult(tee)
    }
    assert(foo0(Bar("xxx")) === "Bar(xxx) sucks!")


    // CONTEXT BOUNDS: bind parameter T with such that implicit Ziltoid[T] is available (same as foo0)
    def foo[T : Ziltoid](tee: T): String = {
      implicitly[Ziltoid[T]].insult(tee)
    }
    assert(foo(Bar("xxx")) === "Bar(xxx) sucks!")

    // see also:
    TypeContextBounds
  }


  // Implicit functions are available as functional values
  {

    def foo(num: Int)(implicit pingx: Int => Bar): String = {
      pingx(num).name
    }
    implicit def ping(num: Int): Bar = new Bar("ping~" + num)

    assert(foo(123) === "ping~123")
  }

}