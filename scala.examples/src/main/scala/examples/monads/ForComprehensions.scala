package examples.monads

object ForComprehensions {
  // For Comprehension:
  for (x <- 1 to 4; y <- 'a' to 'c')
    yield (x, y)

  // alternative syntax:
  for {
    x <- 1 to 4  // Each line like this is a GENERATOR
    y <- 'a' to 'c'
  }
    yield (x, y)

  // is transformed to
  (1 to 5).flatMap(e =>
    ('a' to 'c')
      .map(e2 => (e, e2)))


  // TODO: For expression, changing from one collection type to another:
//  val result: Vector[_] = for (x <- List(1, 2, 3)) yield x
}