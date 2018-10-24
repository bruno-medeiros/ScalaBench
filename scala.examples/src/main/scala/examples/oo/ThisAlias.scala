package examples.oo

//noinspection ConvertExpressionToSAM
object ThisAlias extends App {

  trait Foo {
    self => // An alias to 'this', to prevent shadowing

    def foo(): Int

    def copy(): Foo = {
      new Foo() {
        override def foo(): Int = {
          // This will refer to outer foo
          self.foo()
          // This also works I think:
          Foo.this.foo()
        }
      }
    }

  }

  private val parent: Foo = new Foo {
    override def foo(): Int = {
      println("Parent")
      1
    }
  }
  parent.copy().foo()
}