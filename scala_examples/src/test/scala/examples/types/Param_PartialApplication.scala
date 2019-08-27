package examples.types

import examples.types.TypeContextBounds.MyBox

import scala.reflect.runtime.universe._


object Param_PartialApplication extends App {

  def foo[F[_], A](fa: F[A])(implicit ev: TypeTag[F[A]]): String = {
    println(TypeTags.typeOfExp(fa))
    fa.toString
  }

  // foo instantiated with F := [A]Function1[Int, A]
  foo { x: Int => x * 2 }


  // Question: is this specific to Function literals only?
  // looks like that is the case

  class MyBox[A, B](a: A, b: B)
  val box = MyBox(123, "asd")
  // foo instantiated with F := [A]MyBox[Int, A]
  foo2(box)

  def foo2[F[_], A](fa: F[A])(implicit ev: TypeTag[F[Float]]): String = {
    println(typeOf[F[Float]])
    "blah"
  }

}