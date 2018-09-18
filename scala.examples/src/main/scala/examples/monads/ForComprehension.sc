
// list all combinations of numbers x and y where x is drawn from
// 1 to M and y is drawn from 1 to N
for (x <- 1 to 5; y <- 'a' to 'c')
  yield (x, y)

for {
  x <- 1 to 5;
  y <- 'a' to 'c'
}
  yield (x, y)

(1 to 5)
  .flatMap(e =>
    ('a' to 'c').map(e2 => (e, e2)))


for (x <- 1 to 5) yield 'a'

// Generator example:

trait Generator[+T] { self =>
  def generate: T
  def map[S](f: T => S) : Generator[S] = new Generator[S] {
    def generate = f(self.generate)
  }
  def flatMap[S](f: T => Generator[S]) : Generator[S] = new Generator[S] {
    def generate = f(self.generate).generate
  }
}

val integers = new Generator[Int] {
  val rand = new java.util.Random
  def generate = rand.nextInt()
}

val booleans = for {x <- integers} yield x > 0