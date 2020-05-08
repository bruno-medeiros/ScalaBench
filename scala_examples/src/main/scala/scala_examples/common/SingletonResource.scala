package scala_examples.common

import java.util.concurrent.atomic.AtomicInteger

object SingletonResource {
  val resourceId: AtomicInteger = new AtomicInteger(0)
  val resourceCount: AtomicInteger = new AtomicInteger(0)
}

/**
  * A helper to experiment with lifecycle and resource allocation
  */
//noinspection ConvertibleToMethodValue
class SingletonResource(addDisposeListener: (() => Unit) => Unit) {
  import SingletonResource.resourceCount

  def this(composable: ComposableLike) = {
    this(composable.addDisposeListener _)
  }

  val resourceId = SingletonResource.resourceId.incrementAndGet()
  println(s"Creating SingletonResource($resourceId)")
  assert(resourceCount.get == 0)

  resourceCount.incrementAndGet()
  addDisposeListener(this.destroy _)

  var disposed = false

  def destroy(): Unit = {
    println(s"Destroying SingletonResource($resourceId)")
    assert(disposed == false)
    disposed = true
    SingletonResource.resourceCount.decrementAndGet()
  }
}
