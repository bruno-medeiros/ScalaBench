package examples.oo.types

//noinspection NotImplementedCode
object ParameterizedClasses {

  case class MyBox[+T](obj: T) {

    def get(): T = obj

  }

  MyBox[Int](1)
  MyBox(1)   // the type is being inferred, i.e. determined based on the value arguments


  abstract class TopClass(var topParam: String) {}
  class Sub1 extends TopClass("Sub1Param")


  // Bounded types in type parameters:

  // T must derive from TopLevel or be TopLevel
  def myFct1[T <: TopClass](arg: T): T = { ??? }
  // T must be a supertype of Level1
  def myFct2[T >: Sub1](arg: T): T = { ??? }
  def myFct3[T >: Sub1 <: TopClass](arg: T): T = { ??? }

  // TODO: type constraints: <:< =:=


  // TODO: explore generic Numeric classes, and implications of primitive as type arguments
  MyBox[Int](123)

}