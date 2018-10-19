package examples.types

object SelfTypes2 {

  abstract class Graph {
    type Edge
    type Node <: NodeIntf
    abstract class NodeIntf {
      def connectWith(node: Node): Edge
    }
    def nodes: List[Node]
    def edges: List[Edge]
    def addNode(): Node
  }

  abstract class DirectedGraph extends Graph {
    type Edge <: EdgeImpl
    type Node <: NodeImpl
    class EdgeImpl(origin: Node, dest: Node) {
      def from: Node = origin
      def to: Node = dest
    }
    class NodeImpl extends NodeIntf {
      self: Node => // Important, self type

      def connectWith(node: Node): Edge = {
        // The this reference would normally fail because NodeImpl is not a Node
        // but the self type makes it work!

        val edge = newEdge(this, node)
        edges = edge :: edges
        edge
      }
    }
    protected def newNode: Node
    protected def newEdge(from: Node, to: Node): Edge
    var nodes: List[Node] = Nil
    var edges: List[Edge] = Nil
    def addNode(): Node = {
      val node = newNode
      nodes = node :: nodes
      node
    }
  }

  class ConcreteDirectedGraph extends DirectedGraph {
    type Edge = EdgeImpl
    type Node = NodeImpl
    protected def newNode: Node = new NodeImpl
    protected def newEdge(f: Node, t: Node): Edge =
      new EdgeImpl(f, t)
  }

}
