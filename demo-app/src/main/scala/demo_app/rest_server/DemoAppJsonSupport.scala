package demo_app.rest_server

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import demo_app.workspaces.WorkspaceRegistry.CreateWorkspaceInfo
import spray.json.DefaultJsonProtocol

trait DemoAppJsonSupport extends SprayJsonSupport {

  import DefaultJsonProtocol._

  implicit val createWorkspaceInfoJSonFormat = jsonFormat3(CreateWorkspaceInfo)

}