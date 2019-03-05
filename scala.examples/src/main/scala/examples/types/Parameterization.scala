package examples.types

import examples.collections.ArrayExamples

import scala.language.existentials


//noinspection NotImplementedCode,ScalaUnusedSymbol
object Parameterization extends App {

  abstract class TopClass(var topParam: String) {}
  class SubClass extends TopClass("Sub1Param")

  case class ReadBox[+T](obj: T) {
    def get(): T = obj
  }
  val covariant: ReadBox[TopClass] = ReadBox[SubClass](new SubClass)

  case class WriteBox[-T]() {
    def get(obj: T) = { print(obj) }
  }
  val contravariant: WriteBox[SubClass] = WriteBox[TopClass]()


  val intrbox = ReadBox[Int](123)

  // Implications of using primitives as type arguments. This works:
  val intrbox2: ReadBox[AnyVal] = intrbox
  println(intrbox2.obj)

  // See also:
  ArrayExamples

  // Simple bounded types in type parameters:
  {
    // T must derive from TopLevel or be TopLevel
    def myFct1[T <: TopClass](arg: T): T = { ??? }
    // T must be a supertype of Level1
    def myFct2[T >: SubClass](arg: T): T = { ??? }
    def myFct3[T >: SubClass <: TopClass](arg: T): T = { arg }

    myFct3(new SubClass)
  }

  // Infix type instantiation
  {
    case class MyMap[K, V]()

    val _: MyMap[String, Int] = ReadBox[String MyMap Int](null).get()
  }

}