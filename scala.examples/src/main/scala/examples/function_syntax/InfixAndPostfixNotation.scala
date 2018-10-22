package examples.function_syntax

object InfixAndPostfixNotation extends App {

  // Infix notation
  val four = 2.+(2)

  val string = List(1, 2, 3) mkString ","
  assert(string == "1,2,3")

  //Postfix notation:
  println(List(1, 2, 3) length)

}
