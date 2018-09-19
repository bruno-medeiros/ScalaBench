trait Database {
  def url(): String
}

trait DatabaseImpl extends Database {
  override val url: String = "db url"
}


trait MyApp extends Database {
  def open(): Unit = {
    println(url)
  }
}

trait MyApp2 {
  self: Database =>

  def open(): Unit = {
    println(url)
  }
}

new MyApp with DatabaseImpl {}.open()
new MyApp2 with DatabaseImpl {}.open()

new DatabaseImpl with MyApp {}.open()
new DatabaseImpl with MyApp2 {}.open()
