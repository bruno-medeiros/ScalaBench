package examples.types

import scala.reflect.runtime.universe._

// Provide some reflective/runtime info on type parameters
object TypeTags extends App {

  assert(typeOf[List[Int]] =:= typeOf[List[Int]])
  assert(!(typeOf[List[Int]] =:= typeOf[List[String]]))


  // Is there nothing in stdlib's universe that provides this already?
  def typeOfExp[T : TypeTag](v: T): TypeTag[T] = typeTag[T]
  // Alternative definition without context bound sugar
  //def typeOfExp[T](v: T)(implicit tag: TypeTag[T]): TypeTag[T] = tag

  {
    println(typeOfExp(List[Int](1, 2, 3)))
    val anon = new {
      val m = 123
      def foo() {}
    }
    println(typeOfExp(anon)) // TypeTag[AnyRef{val m: Int; def foo(): Unit}]
  }


  // Example of using typeTag to match (runtime reflection
  def matchThing[T : TypeTag](thing: Some[T]) = {
    thing match {
      case Some(value: Int) => "Has int " + value.toString
      case Some(value: Seq[Int] @unchecked) if typeOf[T] =:= typeOf[Seq[Int]] => "Seq of int" + value.sum
      case _ => "Something else"
    }
  }
  assert(matchThing(Some[String]("xxx")) == "Something else")
  assert(matchThing(Some[Int](10)) == "Has int 10")
  assert(matchThing(Some[Seq[Int]](Seq(10))).startsWith("Seq of int"))


  def printParamInfo[T: TypeTag](x: T): Unit = {
    assert(typeOf[T] eq implicitly[TypeTag[T]].tpe)
    val targs = typeOf[T] match { case TypeRef(_, _, args) => args }
    println(s"type of $x has type arguments ${targs.mkString(",")}")
  }
  printParamInfo(Map(1 -> "foo", 4 -> "bar"))

}