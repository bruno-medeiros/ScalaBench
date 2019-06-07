package scala_examples.fixture

import org.scalatest.BeforeAndAfter
import org.scalatest.OneInstancePerTest
import org.scalatest.Suite
import scala_examples.common.Composable

trait ComposableTest extends BeforeAndAfter with OneInstancePerTest {
  this: Suite =>

  val testAfters = new Composable()

  after(testAfters.dispose())

  def addTestAfter(handler: () => Unit): Unit = {
    testAfters.addDisposeListener(handler)
  }

}