package examples.collections

import org.scalatest.funsuite.AnyFunSuite

class Options extends AnyFunSuite {

  // Just an option
  val opt: Option[(String, Int)] = Some("abc", 123)

  test("patterns - operation on Option contents") {
    // One way running operation on contents
    // (note: type annotation not required, it's only for clarity)
    opt.foreach((elem: (String, Int)) => {
      val (name, age) = elem
      println(name + " " + age)
    })

    // another way, this is preferable since it can unwrap tuples directly:
    // (note: type annotation not required, it's only for clarity)
    for ((name: String, age: Int) <- opt) {
      println(name + " " + age)
    }
  }
}
