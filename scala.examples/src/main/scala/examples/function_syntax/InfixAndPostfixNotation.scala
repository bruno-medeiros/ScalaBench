package examples.function_syntax

import scala.language.postfixOps

object InfixAndPostfixNotation extends App {

  // Infix notation
  val four = 2.+(2)

  val string = List(1, 2, 3) mkString ","
  assert(string == "1,2,3")


  //Postfix notation:
  println(List(1, 2, 3) length)

  {
    import scala.concurrent.duration._

    println(60 minutes)
    println(60.minutes)
  }

  // Postfix discouraged, since compiler might try to compile as infix
  var what = Seq(1, 2, 3) toList 4
  // Intellij thinks the above is infix, as of this writing,
  // but it is postfix invocation of List::apply

}