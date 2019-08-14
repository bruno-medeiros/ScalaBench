package demo_app.rest_server
import demo_app.workspaces.WorkspaceRegistry

trait DemoAppApi {

  def listElements(expectedEntity: String): Unit

  def createElement(createReq: WorkspaceRegistry.CreateWorkspaceInfo): Unit

  def deleteWorkspace(nameId: String): Any

  def getElement(nameId: String): Any
}
