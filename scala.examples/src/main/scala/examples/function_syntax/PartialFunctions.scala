package examples.function_syntax

object PartialFunctions extends App {

  // Partial functions
  var partialFn: PartialFunction[Int, String] = { case 1 => "one"}
  assert(partialFn.isDefinedAt(1))
  assert(!partialFn.isDefinedAt(2))

  var partialFn_upcast: Int => String = { case 1 => "one" }

  partialFn_upcast.apply(1)


  // Using partial function syntax as a shorthand to unpack a tuple
  {
    val tuples = List((3, "foo"), (4, "xpto"))
    val tuplesSwapped = List(("foo", 3), ("xpto", 4))
    // Standard using regular lambda:
    assert(tuples.map(t => (t._2, t._1)) == tuplesSwapped)

    // Alternative syntax using partial functions
    assert(tuples.map{ case (a, b) => (b, a) } == tuplesSwapped)
    // Another alternative, using tupled:
    assert(tuples.map(((a: Int, b: String) => (b, a)).tupled) == tuplesSwapped)

    // It's not typesafe though, pattern might not be exhaustive (there is warning though)
    //tuples.map{ case (3, b) => (b, 3) }
  }
}