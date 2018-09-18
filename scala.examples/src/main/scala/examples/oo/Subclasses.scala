package examples.oo

abstract class TopClass {
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

class Sub1 extends TopClass {
  def method1(x: Int): Int = {
    println("Method 1")
    11
  }
  // TopLevel's method2 needs to be explicitly overridden
  override def method2(x: Int): Int = {
    22
  }
}
