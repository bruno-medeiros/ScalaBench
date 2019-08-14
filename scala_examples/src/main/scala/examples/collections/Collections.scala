package examples.collections

object Collections extends App {

  val x = List.fill(1000)(42)

  assert(x.sizeIs > 10)

}
