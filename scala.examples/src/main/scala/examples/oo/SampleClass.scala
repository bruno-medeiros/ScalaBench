package examples.oo

class SampleClass(var strParam: String) {
  var strField = "original"

}

object SampleClass {
  private val sample = new SampleClass("blah")
  sample.strField = "modified";
  sample.strParam = "modified param"
}