package examples.oo

// Constructor example, with class parameter and class field
class SampleClass(var strParam: String) {
  var strField = "original2"
}

object SampleClass extends App {
  private val sample = new SampleClass("blah")
  sample.strField = "modified field"
  sample.strParam = "modified param"

  new MyPrivate(123)
}

// Private constructor:
class MyPrivate private(strField: String) {

  // with public constructor
  def this(num: Integer) {
    this("with number")
    println("In private constructor")
  }
}
