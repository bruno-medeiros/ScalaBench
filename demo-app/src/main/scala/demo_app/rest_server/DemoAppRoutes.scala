package demo_app.rest_server

import akka.actor.ActorSystem
import akka.actor.typed.ActorRef
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.event.Logging
import demo_app.workspaces.WorkspaceRegistry

class DemoAppRoutes(system: ActorSystem, workspaceRegistry: ActorRef[WorkspaceRegistry.Msg]) {

  val log = Logging(system, classOf[DemoAppRoutes])

  val routes: Route = path("workspaces") {
    //    path("list") {
    //      get {
    //        complete()
    //      }
    //    }
    get {
      complete("Hello, World!")
    }
  }

}