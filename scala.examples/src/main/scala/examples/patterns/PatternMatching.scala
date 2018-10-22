package examples.patterns

object PatternMatching extends App {

  sealed abstract class Node
  case class NodeFoo(name: String) extends Node
  case class NodeBar(id: Int, size: Int) extends Node
  case class NodeOther(misc: Double) extends Node

  private def matchNode(node: Node): Unit = {
    // Note: each case line is a PATTERN (until the "=>")

    node match {
      case NodeFoo("666") => println("Evil NodeFoo!!")
        // Pattern guard
      case NodeFoo(name) if name.startsWith("Special") => println("Special NodeFoo!!")
        // More generic case should be after the more specific ones
      case NodeFoo(name) => println(name)

      case NodeBar(id, _) if id == 333 => println("Special NodeBar 333!")
        // generic match for NodeBar:
      case NodeBar(666, _) => println("Evil NodeBar")
        // Matching on type only:
      case nodeBar : NodeBar => println(s"Generic nodeBar: ${nodeBar.id}")
    }
  }

  // Matching (on case class)
  matchNode(NodeFoo("MyFoo"))
  matchNode(NodeFoo("Special"))
  matchNode(PatternMatching.NodeBar(333, 60))
  matchNode(PatternMatching.NodeBar(123, 60))


  //noinspection VariablePatternShadow
  // match with back-tick:
  def matchWithBackTick(foo: Int, bar: String): Unit = {
    (123, "abc")  match {
        // `foo` is ref, foo2 is declaration from pattern
      case (foo2 @ `foo`, bar) =>
        println(s"Match any, assigning to $foo2 , $bar")
    }
  }

  object PatternMatching_InVarDeclarations extends App {

    // Matching in var declarations:
    val stationNames = List("Paris", "Geneva", "London")
    val list @ List(paris, geneva, london, other) = stationNames // error

  }
}

