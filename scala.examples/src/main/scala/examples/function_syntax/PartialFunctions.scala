package examples.function_syntax

object PartialFunctions extends App {

  // Partial functions
  var partialFn: Int => String = { case 1 => "one" }
  // if used as predicate they become full functions:
  ('a' to 'g').zipWithIndex.filter { case (_, ix) =>  ix % 2 == 0 }
  // TODO: how, which type?

  var partialFn2: Int => Boolean = { case 1 => true }
  partialFn2(123) // This still blows with MatchError

}