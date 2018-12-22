package examples.oo.types

//noinspection NotImplementedCode
object ParameterizedClasses {

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


  // Bounded types in type parameters:

  // T must derive from TopLevel or be TopLevel
  def myFct1[T <: TopClass](arg: T): T = { ??? }
  // T must be a supertype of Level1
  def myFct2[T >: SubClass](arg: T): T = { ??? }
  def myFct3[T >: SubClass <: TopClass](arg: T): T = { ??? }


  // TODO: type constraints: <:< =:=


  // TODO: explore generic Numeric classes, and implications of primitive as type arguments

  // TODO: ReadBox[_]
  ReadBox[Int](123)

}