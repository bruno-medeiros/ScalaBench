package examples.oo.types

import scala.reflect.runtime.universe._

// Provide some reflective/runtime info on type parameters
object TypeTags extends App {

  def paramInfo[T](x: T)(implicit tag: TypeTag[T]): Unit = {
    assert(tag eq tag)
    val targs = tag.tpe match { case TypeRef(_, _, args) => args }
    println(s"type of $x has type arguments ${targs.mkString(",")}")
  }
  // This is same as above
  def paramInfo2[T: TypeTag](x: T): Unit = {
    assert(typeOf[T] eq implicitly[TypeTag[T]].tpe)
    val targs = typeOf[T] match { case TypeRef(_, _, args) => args }
    println(s"type of $x has type arguments ${targs.mkString(",")}")
  }

  paramInfo(Map(1 -> "foo", 4 -> "bar"))
  paramInfo2(Map(1 -> "foo", 4 -> "bar"))

}