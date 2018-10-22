package basic

//noinspection ScalaUnusedSymbol
class MyClass(x: Int, y: Int) {           // Defines a new type MyClass with a constructor
  require(y > 0, "y must be positive")    // precondition, triggering an IllegalArgumentException if not met  

  // auxiliary constructor
  def this (x: Int) = {
    this(1, 2)
    println("MyClass created with auxiliary constructor")
  }
  def nb1: Int = x                             // public method computed every time it is called
  def nb2: Int = y
  def addAll(a: Int): Int = { a + x + y } // private method

  val nb3: Int = x + y                         // computed only once
  override def toString: String =                 // overridden method
      x + ", " + y
}

object MyClass {

  def xxx(): String = "12"

}