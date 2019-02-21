package examples.monads

//noinspection ZeroIndexToHead
object ForComprehensions extends App {

  // For Comprehension:
  for (x <- 1 to 4; y <- 'a' to 'c')
    yield (x, y)

  // alternative syntax (does not require semicolon) :
  for {
    x <- 1 to 4 if x >= 2  // Each line like this is a GENERATOR
    y <- 'a' to 'c'
  } yield (x, y)

  // is transformed to
  (1 to 5).flatMap(e =>
    ('a' to 'c')
      .map(e2 => (e, e2)))

  // Shadowing of vars allowed:
  for {
    x <- Some(true)
    x <- Some(false)
  } yield x


  // Pattern matching is an implicit filter:
  {
    val result = for {
      (a, b) <- List((1, 2), 3, "abc", (10, 8))
    } yield a.toString + b.toString

    assert(result == List("12", "108"))
  }

  // TODO: For expression, changing from one collection type to another:
//  val result: Vector[_] = for (x <- List(1, 2, 3)) yield x
}
