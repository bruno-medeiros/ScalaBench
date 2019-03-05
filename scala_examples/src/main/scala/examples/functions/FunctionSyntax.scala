package examples.functions

//noinspection SimplifyBoolean
object FunctionSyntax extends App {

  // Basic Syntax:
  def someFoo(a: String): Int = a.length
  def f1 = (a: String) => a.length
  val f1b: String => Int = a => a.length
  // Shorthand for def returning Unit
  def unitReturnFunc(a: String) { println(a) }


  // Empty parens:
  def empty1(): Int = { println("Empty1"); 1 }
  def empty2: Int = { println("Empty2"); 2 }
  empty1()
  empty1  // Warning: should be called with parenthesis
  empty2  // Error if parenthesis add, it's a no-parens method


  // Currying:
  def curryAdd(x: Int)(y: Int): Int = x + y
  curryAdd(1) _ // Must use underscore, or:
  val x: Int => Int = curryAdd(1) // explicit type parameter


  // Reference lambda from function def:
  def foo(a: Int): Int = a * 2
  // In the absence of target type, then `_` must be used:
  val fooLambda1 = foo _
  // If target type is present, `_` is not required:
  val fooLambda2: Int => Int = foo


  // ========== Interaction with tuples:
  def bar(a: Int, b: Int): Int = a + b
  // Use tupled to transform parameter list to a tuple:
  val barLambda = (bar _).tupled
  val barLambdaExplicity: ((Int, Int)) => Int = barLambda
  assert(barLambda((2, 4)) == 6)
  // This doesn't work though:
//  val barLambdaExplicity: (Int, Int) => Int = barLambda

  // Another example:
  private val tuples: List[(String, Int)] = List(("one", 1), ("two", 2))
  tuples.map{ case (a, b) => a.length + b}
  tuples.map(t => t._1.length + t._2)
  tuples.map(((a: String, b: Int) => a.length + b).tupled)
//  tuples.map(((a, b) => a.length + b).tupled)

  def tupled[T1, T2, R](f: (T1, T2) => R) = { f.tupled }

  tuples.map(tupled((a, b) => a.length + b))

  // Using argument list, that gets transform into a tuple
  {
    import scala.reflect.classTag

    def foo[A](param: A) = classTag[Tuple2[Int, Int]].runtimeClass.isInstance(param)
    assert(foo(1) == false)
    assert(foo(1, 2) == true)
  }
}