package examples.patterns

object PatternMatching extends App {

  sealed abstract class Node
  case class NodeFoo(name: String) extends Node
  case class NodeBar(id: Int, size: Int) extends Node
  case class NodeOther(misc: Double) extends Node

  private def matchNode(node: Node): String = {
    // Note: each case line is a PATTERN (until the "=>")

    node match {
        // Specific match
      case NodeFoo("Special") => "Special NodeFoo!"
        // More generic:
      case NodeFoo(name) => name

      // Pattern guard
      case NodeBar(id, _) if id == 333 => "Special NodeBar 333!"
        // generic match for NodeBar:
      case NodeBar(666, _) => "Evil NodeBar"
        // Matching on type only:
      case nodeBar : NodeBar => s"Generic nodeBar: ${nodeBar.id}"
    }
  }

  // Matching (on case class)
  assert(matchNode(NodeFoo("MyFoo")) == "MyFoo")
  assert(matchNode(NodeFoo("Special")) == "Special NodeFoo!")
  assert(matchNode(PatternMatching.NodeBar(333, 60)) == "Special NodeBar 333!")
  assert(matchNode(PatternMatching.NodeBar(123, 60)) == "Generic nodeBar: 123")


  // Sequence matching:
  List(1, 2, 3) match { case list @ List(1, _*) => println(list) }

  // match with back-tick:
  {
    val expected: Integer = new Integer(123)

    new Integer(123) match {
      // back-tick makes it a ref, so expected is a ref
      case `expected` => assert(true)
      case _ => assert(false)
    }

    // note that this var has to be
    val expectedNum: Number = expected
    BigInt(123) match {
      // Matching `expected` doesn't work because `expected` is typed as Integer
//      case `expected` => assert(false)

      // But this works because it's typed as Number, which can match BigInt
      case `expectedNum` => assert(true)
      case _ => assert(false)
    }
  }

  {
    // Pattern binder:
    ("foo", 123) match {
      // back-tick makes it a ref, so expected is a ref
      case redefined @ ("foo", _) => assert(redefined == ("foo", 123))
      case _ => assert(false)
    }
  }


  // Matching type PARAMETERS!:
  {
    val x = Map[String, Int]()
    x match  {
      case m: Map[typeA, typeB] =>
//        Map[typeB, typeA](1 -> "foo")
        Map[typeB, typeA]() // TODO: what can be done here?...

    }
  }
}