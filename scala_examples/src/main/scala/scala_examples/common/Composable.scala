package scala_examples.common

trait ComposableLike extends AutoCloseable {
  var disposed = false
  var handlers: Vector[() => Unit] = Vector[() => Unit]()

  def addDisposeListener(handler: () => Unit): Unit = {
    handlers :+= handler
  }

  def dispose(): Unit = {
    disposed = true
    handlers.foreach(h => h())
  }

  override def close(): Unit = dispose()
}

class Composable extends ComposableLike
