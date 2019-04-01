package akka_examples.common

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.{AsyncWordSpecLike, BeforeAndAfterAll, Matchers}

class AkkaAsyncTest(_system: ActorSystem)
  extends TestKit(_system)
  with Matchers
  with AsyncWordSpecLike
  with BeforeAndAfterAll {

  def this() = this(ActorSystem("AkkaTest"))

  override def afterAll: Unit = {
    shutdown(system)
  }
}