package examples.oo


object ClassConstruction extends App {

  // Private constructor:
  class MyPrivate private(strField: String) {

    // with public constructor
    def this(num: Integer) {
      this("with number")
      println("In private constructor")
    }
  }

  // Constructor example, with class parameter and class field
  class Construction(var strParamField: String, strParam: Int) {
    var strField = "original2"
  }

  private val sample = new Construction("blah", 123)
  sample.strField = "modified field"
  sample.strParamField = "modified param"
  //sample.strParam is not a field

  new MyPrivate(123)

  // TODO Review this some more
}
