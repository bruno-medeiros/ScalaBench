package examples

object MultipleVarDeclarations extends App {

  var counter = 0

  val a, b, c = new Object {
    counter += 1
    println("Created")
  }
  assert(counter == 3)


  // Tuples (have to use extra parenthesis on right side to avoid warning
  assert(("abc", 123) == ("abc", 123))

}