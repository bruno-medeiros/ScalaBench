//package cats_examples
//
//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.IO
//
//object CatsBracketExample extends App {
//
//  class Foo {
//
//    println("Created")
//
//    def data(): String = {
//      "Blah"
//    }
//
//    def close(): Unit = {
//      println("Closed")
//    }
//  }
//
//  val io = IO[Foo] { new Foo }
//
//  val io2 = io.bracket(foo =>
//    IO {
//      println(foo.data())
//    }
//  )(foo =>
//    IO {
//      foo.close()
//    }
//  )
//
//  io2.unsafeRunSync()
//
//}
