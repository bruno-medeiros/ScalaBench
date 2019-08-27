package examples.oo

object Inheritance extends App {

  abstract class TopClass(var topParam: String) {
    val topVal = "topVal"
    var topVar = "topVar"
    var x = 10

    def method1(x: Int): Int
    def method2(x: Int): Int = {
      println("In method2")
      123
    }

    def otherMethod: String = {
      print("In TopClass::otherMethod... ")
      s"$x"
    }
  }

  class Sub1 extends TopClass("TopParam:Sub1") {
    // Implementing
    def method1(x: Int): Int = {
      println("Method 1")
      11
    }
    // TopLevel's method2 needs to be explicitly overridden
    override def method2(x: Int): Int = {
      22
    }

    override def otherMethod: String = {
      "Sub::otherMethod"
    }

    // Not allowed:
    //  override def topVal(): String = {
    //    "topVal:Sub1"
    //  }

    //    override def topVar(): String = {
    //      "topVal:Sub1"
    //    }

  }

  private val sub1 = new Sub1()

  println("Other: " + sub1.otherMethod)
}