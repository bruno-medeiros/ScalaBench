package examples.libraries


object ScalacticExample extends App {

  val fooInt: Int = 1
  val fooLong: Int = 1

  {
    import org.scalactic.TypeCheckedTripleEquals._
    assert(fooInt === fooLong)
  }

  {
    import org.scalactic.TripleEquals._
    // This line won't compile without the import above,
    // because types don't match
    assert(fooInt === fooLong)
  }
}