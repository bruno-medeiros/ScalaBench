package examples.oo.types

object SelfTypes2 extends App {

  trait Database {
    def url(): String
  }

  trait DatabaseImpl extends Database {
    override val url: String = "db url"
  }


  trait MyApp extends Database {
    def open(): Unit = {
      println(url())
    }
  }

  // I think the difference from MyApp above is that this MyAppWithSelfType
  // allows a more flexible trait linearization,
  // whereas myApp forces it to be MyApp -> Database
  //
  // Addendum: ACTUALLY, self type reference main use is that it allows a
  // reference to a non-concrete type (a type parameter, or abstract type!),
  // whereas extend only allows a concrete type.
  trait MyAppWithSelfType {
    // Note: it can also be this: Database
    self: Database =>

    def open(): Unit = {
      println(url())
    }
  }


  new MyApp with DatabaseImpl {}.open()
  new MyAppWithSelfType with DatabaseImpl {}.open()

  new DatabaseImpl with MyApp {}.open()
  new DatabaseImpl with MyAppWithSelfType {}.open()

}