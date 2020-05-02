package demo_app.rest_server

import scala.concurrent.duration._
import scala.util.Try

import akka.actor.typed
import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.AskPattern._
import akka.http.scaladsl.server.Directives.{ entity, post, _ }
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import demo_app.workspaces.WorkspaceRegistry.CreateWorkspaceInfo
import demo_app.workspaces.{ Workspace, WorkspaceRegistry }

class DemoAppRoutes(
  workspaceRegistry: ActorRef[WorkspaceRegistry.Msg]
)(implicit system3: typed.ActorSystem[_])
    extends DemoAppJsonSupport {

  implicit lazy val timeout = Timeout(5.seconds) // usually we'd obtain the timeout from the system's configuration
  implicit val ec = system3.executionContext

  val routes: Route = concat(
    pathPrefix("workspaces") {
      concat(
        pathEnd {
          get {
            extractLog { implicit log =>
              log.info(s"ListWorkspaces")

              val result = workspaceRegistry.ask[Iterable[String]](WorkspaceRegistry.ListWorkspaces)

              complete(result.map(_.mkString("[", ",", "]")))
            }
          }
        },
        pathEnd {
          post {
            entity(as[CreateWorkspaceInfo]) { createInfo =>
              extractLog { implicit log =>
                log.info(s"CreateWorkspace:\n $createInfo \n------")

                val result = workspaceRegistry
                  .ask[Try[Workspace]](WorkspaceRegistry.CreateWorkspace(createInfo, _))
                  .transform(s => s.flatten)

                complete(result.map("CreatedWorkspace: " + _.name))
              }
            }
          }
        },
        path(Segment) {
          name =>
            concat(
              get {
                extractLog { implicit log =>
                  log.info(s"GetWorkspace: $name")

                  val result = workspaceRegistry.ask[Option[Workspace]](WorkspaceRegistry.GetWorkspace(name, _))

                  rejectEmptyResponse {
                    complete(result.map(_.map(wks => s"GetWorkspace result: " + wks.name)))
                  }
                }
              },
              delete {
                extractLog { implicit log =>
                  log.info(s"DeleteWorkspace: $name")

                  val result = workspaceRegistry.ask[Try[Unit]](WorkspaceRegistry.DeleteWorkspace(name, _))

                  complete(result.map(_ => "Success"))
                }
              },
            )
        },
      )
    },
  )

}
