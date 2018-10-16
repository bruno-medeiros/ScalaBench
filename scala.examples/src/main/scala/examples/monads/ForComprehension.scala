package examples.monads

object ForComprehension {
  // For Comprehension:
  for (x <- 1 to 4; y <- 'a' to 'c')
    yield (x, y)

  // alternative syntax:
  for {
    x <- 1 to 4
    y <- 'a' to 'c'
  }
    yield (x, y)

  // is transformed to
  (1 to 5).flatMap(e =>
    ('a' to 'c')
      .map(e2 => (e, e2)))

}
