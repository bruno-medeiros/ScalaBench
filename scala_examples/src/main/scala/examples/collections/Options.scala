package examples.collections

import org.scalatest.FunSuite

class Options extends FunSuite {

  // Just an option
  val opt: Option[Int] = Some(123)

  test("patterns - operation on Option contents") {
    // One way of running in contents
    // (note: type annotation not required, it's only for clarity)
    opt.foreach((opt: Int) => {
      print("here:" + opt)
    })

    // another way:
    // (note: type annotation not required, it's only for clarity)
    for (opt: Int <- opt) {
      print("here:" + opt)
    }
  }
}
