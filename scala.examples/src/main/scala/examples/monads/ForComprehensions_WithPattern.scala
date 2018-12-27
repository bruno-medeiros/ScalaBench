package examples.monads

object ForComprehensions_WithPattern extends App {

  object MyExtractor {
    def unapply(arg: String): Option[String] = arg match {
      case _ if arg.length >= 4 => Some(arg)
      case _ => None
    }
  }

  // PATTERNs can be used in the left side of the GENERATOR
  // acting as filter and modifier, for the elements generated.
  val x =
  for {
    MyExtractor(a) <- List("Joe", "Mary", "Brian", "Ana")
  }
    yield a

  assert(x == List("Mary", "Brian"))
}