package demo_app.workspaces

import akka.actor.testkit.typed.scaladsl.BehaviorTestKit
import akka.actor.testkit.typed.scaladsl.TestInbox
import org.scalatest.FunSuite
import org.scalatest.Matchers
import org.scalatest.OneInstancePerTest

import scala.util.Try

class WorkspaceRegistryTests extends FunSuite
  with Matchers
  with OneInstancePerTest
{

  val registryReg = new WorkspaceRegistry
  val testKit = BehaviorTestKit(registryReg)

  test("CreateWorkspace and ListWorkspaces") {

    listWorkspaces(Set())

    {
      val reply = TestInbox[Try[Workspace]]()
      testKit.run(WorkspaceRegistry.CreateWorkspace("foo", reply.ref))
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

    testKit.run(WorkspaceRegistry.CreateWorkspace("foo", TestInbox().ref))
    testKit.run(WorkspaceRegistry.CreateWorkspace("bar", TestInbox().ref))

    listWorkspaces(Set("foo", "bar"))
  }

  test("DeleteWorkspace") {

    testKit.run(WorkspaceRegistry.CreateWorkspace("foo", TestInbox().ref))
    testKit.run(WorkspaceRegistry.CreateWorkspace("bar", TestInbox().ref))

    testKit.run(WorkspaceRegistry.DeleteWorkspace("foo", TestInbox().ref))
    listWorkspaces(Set("bar"))
  }
}