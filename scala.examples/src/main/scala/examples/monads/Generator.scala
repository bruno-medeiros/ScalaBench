package examples.monads

trait Generator[+T] { self =>
  def generate: T
  def map[S](f: T => S) : Generator[S] = new Generator[S] {
    def generate = f(self.generate)
  }
  def flatMap[S](f: T => Generator[S]) : Generator[S] = new Generator[S] {
    def generate: S = f(self.generate).generate
  }
}

object GeneratorApp extends App {

  val integers: Generator[Int] = new Generator[Int] {
    val rand = new java.util.Random
    def generate: Int = rand.nextInt()
  }

  val gen = for {x <- integers} yield x

  println(gen.generate)
}

