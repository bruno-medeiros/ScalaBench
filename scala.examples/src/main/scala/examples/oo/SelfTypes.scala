package examples.oo


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
trait MyAppWithSelfType {
  // Note: it can also be this: Database
  self: Database =>

  def open(): Unit = {
    println(url())
  }
}

object SelfTypes extends App {

  new MyApp with DatabaseImpl {}.open()
  new MyAppWithSelfType with DatabaseImpl {}.open()

  new DatabaseImpl with MyApp {}.open()
  new DatabaseImpl with MyAppWithSelfType {}.open()

}