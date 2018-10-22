package examples.function_syntax

object PartialFunctions extends App {

  // Partial functions
  var partialFn: PartialFunction[Int, String] = { case 1 => "one"}
  assert(partialFn.isDefinedAt(1))
  assert(!partialFn.isDefinedAt(2))

  var partialFn_upcast: Int => String = { case 1 => "one" }

  partialFn_upcast.apply(1)
}