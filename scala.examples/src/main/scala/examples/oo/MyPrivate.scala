package examples.oo


class MyPrivate private(strField: String) {

  def this(num: Integer) {
    this("with number")

    println("Private constructor")
  }
}
