package examples.oo

object ThisAlias {

  trait Foo {
    self => // An alias to 'this', to prevent shadowing

    def foo(): Int

    def copy(): Unit = {
      new Foo {
        override def foo(): Int = {
          // This will refer to outer foo
          self.foo()
        }
      }
    }
  }
}