package info

abstract class TopLevel {     // abstract class
  def method1(x: Int): Int   // abstract method
  def method2(x: Int): Int = { println("In method2"); 123 }
}

class Level1 extends TopLevel {
  def method1(x: Int): Int = {
    println("Method 1")
    11
  }
  // TopLevel's method2 needs to be explicitly overridden
  override def method2(x: Int): Int = {
    22
  }
}

