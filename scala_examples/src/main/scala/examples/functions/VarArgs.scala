package examples.functions

object VarArgs {

  def printAll(args: String*): Unit = {
    args.foreach(println(_))
  }

  printAll("One", "Two", "Three")

  // pass the sequence to the varargs field
  val fruits = List("apple", "banana", "cherry")
  printAll(fruits: _*)

}