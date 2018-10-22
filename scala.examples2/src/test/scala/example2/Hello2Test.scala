package example2

import org.specs2.mutable.Specification

class Hello2Test extends Specification {

  override def is = "Specs2 description for Hello2"

  Hello2.greeting must startWith("Hello2")
}
