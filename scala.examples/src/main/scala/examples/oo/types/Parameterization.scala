package examples.oo.types


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


  // TODO: explore generic Numeric classes, and implications of primitives as type arguments

  ReadBox[Int](123)


  // Simple bounded types in type parameters:
  {
    // T must derive from TopLevel or be TopLevel
    def myFct1[T <: TopClass](arg: T): T = { ??? }
    // T must be a supertype of Level1
    def myFct2[T >: SubClass](arg: T): T = { ??? }
    def myFct3[T >: SubClass <: TopClass](arg: T): T = { ??? }

    myFct3(new SubClass)
  }


  // Wild card parameter:
  {
    val rb2: ReadBox[_] = ReadBox[Int](123)
    val x: Any = rb2.obj
    assert(x.isInstanceOf[Object])
  }


  // Infix type instantiation
  {
    case class MyMap[K, V]()

    val _: MyMap[String, Int] = ReadBox[String MyMap Int](null).get()
  }

}