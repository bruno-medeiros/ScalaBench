package examples.oo

abstract class TopClass(var topParam: String) {
  val topVal = "topVal"
  var topVar = "topVar"
  var x = 10

  def method1(x: Int): Int
  def method2(x: Int): Int = {
    println("In method2")
    123
  }

  def otherMethod: Int = {
    print("In otherMethod... ")
    x += 2
    x
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

  override def otherMethod: Int = {
    1000
  }

  // Not allowed:
//  override def topVal(): String = {
//    "topVal:Sub1"
//  }

//    override def topVar(): String = {
//      "topVal:Sub1"
//    }

}

object Blah {
  private val sub1 = new Sub1()
  private val other: Int = sub1.otherMethod

  println("Other: " + other)
}