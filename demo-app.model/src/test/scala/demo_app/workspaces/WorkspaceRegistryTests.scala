package demo_app.workspaces

import scala.util.Try

import akka.actor.testkit.typed.scaladsl.{ BehaviorTestKit, TestInbox }
import demo_app.workspaces.WorkspaceRegistry.CreateWorkspaceInfo
import org.scalatest.OneInstancePerTest
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should

class WorkspaceRegistryTests extends AnyFunSuite with should.Matchers with OneInstancePerTest {

  val registryReg = new WorkspaceRegistry
  val testKit = BehaviorTestKit(registryReg)

  def cwDto(str: String): WorkspaceRegistry.CreateWorkspaceInfo = {
    CreateWorkspaceInfo(str, None, 123)
  }

  test("CreateWorkspace and ListWorkspaces") {

    listWorkspaces(Set())

    {
      val reply = TestInbox[Try[Workspace]]()
      testKit.run(WorkspaceRegistry.CreateWorkspace(cwDto("foo"), reply.ref))
      reply.receiveMessage().get.name should ===("foo")
    }

    {
      listWorkspaces(Set("foo"))

      val getWorkspaceReply = TestInbox[Option[Workspace]]()
      testKit.run(WorkspaceRegistry.GetWorkspace("foo", getWorkspaceReply.ref))
      getWorkspaceReply.receiveMessage().get.name should ===("foo")
    }
  }

  def listWorkspaces(names: Set[String]) = {
    val listReply = TestInbox[Iterable[String]]()
    testKit.run(WorkspaceRegistry.ListWorkspaces(listReply.ref))
    listReply.expectMessage(names)
  }

  test("List Workspaces") {

    testKit.run(WorkspaceRegistry.CreateWorkspace(cwDto("foo"), TestInbox().ref))
    testKit.run(WorkspaceRegistry.CreateWorkspace(cwDto("bar"), TestInbox().ref))

    listWorkspaces(Set("foo", "bar"))
  }

  test("DeleteWorkspace") {

    testKit.run(WorkspaceRegistry.CreateWorkspace(cwDto("foo"), TestInbox().ref))
    testKit.run(WorkspaceRegistry.CreateWorkspace(cwDto("bar"), TestInbox().ref))

    testKit.run(WorkspaceRegistry.DeleteWorkspace("foo", TestInbox().ref))
    listWorkspaces(Set("bar"))
  }
}
