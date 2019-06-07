package demo_app.rest_server
import demo_app.workspaces.WorkspaceRegistry

trait DemoAppApi {

  def listElements(expectedEntity: String)

  def createElement(createReq: WorkspaceRegistry.CreateWorkspaceInfo)

  def deleteWorkspace(nameId: String): Any

  def getElement(nameId: String): Any
}
