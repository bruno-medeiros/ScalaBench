ThisBuild / organization := "com.github.bruno-medeiros"
ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.1"
ThisBuild / scalacOptions ++= Seq(
  "-encoding",
  "UTF-8", // source files are in UTF-8
  "-deprecation", // warn about use of deprecated APIs
  "-unchecked", // warn about unchecked type parameters
  "-feature", // warn about misused language features
  "-language:postfixOps",
  "-language:higherKinds", // allow higher kinded types without `import scala.language.higherKinds`
  "-Xlint:_,-adapted-args", // enable handy linter warnings
//  "-Werror",     // turn compiler warnings into errors
)

ThisBuild / Test / logBuffered := false

lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.1.0"
lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.14.0"

val doobieVersion = "0.8.6"

def commonProject(id: String, base: java.io.File) =
  sbt
    .Project(id, base)
    .settings()

lazy val scala_examples = commonProject("scala_examples", file("scala_examples"))
  .settings(
    libraryDependencies += scalaTest,
    libraryDependencies += scalaCheck % Test,
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "2.0.0",
      "org.tpolecat" %% "doobie-core" % doobieVersion,
      "org.tpolecat" %% "doobie-postgres" % doobieVersion,
      "org.tpolecat" %% "doobie-h2" % doobieVersion % Test,
    ),
    scalacOptions := scalacOptions.value
      .filterNot(_ == "-Xfatal-warnings")
      ++ Seq("-Ywarn-unused:-implicits,-locals,-privates"),
    testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-l", "FailureExamples"),
  )

lazy val akkaVersion = "2.5.24"

lazy val akka_examples = commonProject("akka_examples", file("akka_examples"))
  .settings(
    libraryDependencies += scalaTest % Test,
    libraryDependencies += scalaCheck % Test,
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    ),
    libraryDependencies += "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
    scalacOptions := scalacOptions.value
      .filterNot(_ == "-Xfatal-warnings"),
  )

// -------------------- Demo App

val circeVersion = "0.12.0-RC2"
val circeLibs = Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

lazy val demo_app_model_dtos = commonProject("demo-app_model_dtos", file("demo-app.model.dtos"))
  .settings(
    name := "demo-app.model.dtos",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += scalaCheck % Test,
    libraryDependencies ++= circeLibs,
  )

val akkaHttpVersion = "10.1.8"
val akkaDeps = Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
)

lazy val demo_app_model = commonProject("demo-app_model", file("demo-app.model"))
  .settings(
    name := "demo-app.model",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += scalaCheck % Test,
    libraryDependencies ++= akkaDeps
  )
  .dependsOn(demo_app_model_dtos)

// See: https://www.codemunity.io/tutorials/dockerising-akka-http/

lazy val demo_app = commonProject("demo-app", file("demo-app"))
  .enablePlugins(JavaAppPackaging, AshScriptPlugin)
  .settings(
    name := "demo-app",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += scalaCheck % Test,
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
      "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
    ),
    mainClass in Compile := Some("demo_app.rest_server.DemoAppMain"),
    Test / fork := true,
    packageName in Docker := "dockerised-demo-app",
    dockerBaseImage := "openjdk:8-jre-alpine",
    dockerExposedPorts ++= Seq(9000, 9000),
  )
  .dependsOn(demo_app_model)

lazy val scala_bench = (project in file("."))
  .aggregate(
    scala_examples,
    akka_examples,
    demo_app,
  )
