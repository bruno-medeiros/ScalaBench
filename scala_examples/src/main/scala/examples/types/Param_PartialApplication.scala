package examples.types

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

}