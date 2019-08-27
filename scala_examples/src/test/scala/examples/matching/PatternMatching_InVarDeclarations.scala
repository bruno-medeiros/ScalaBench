package examples.matching

object PatternMatching_InVarDeclarations extends App {

  // Matching in var declarations:
  val stationNames = List("Paris", "Geneva", "London")
  val list @ List(paris, geneva, london, other) = stationNames // error

}
