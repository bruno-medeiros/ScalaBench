
ThisBuild / organization := "com.github.bruno-medeiros"
ThisBuild / version      := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.8"
ThisBuild / scalacOptions ++= Seq(
  "-encoding", "UTF-8",   // source files are in UTF-8
  "-deprecation",         // warn about use of deprecated APIs
  "-unchecked",           // warn about unchecked type parameters
  "-feature",             // warn about misused language features
  "-language:postfixOps",
  "-language:higherKinds",// allow higher kinded types without `import scala.language.higherKinds`
  "-Xlint:_,-adapted-args",    // enable handy linter warnings
//  "-Xfatal-warnings",     // turn compiler warnings into errors
//  "-Ypartial-unification", // allow the compiler to unify type constructors of different arities
)


lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.14.0"

lazy val scala_examples = (project in file("scala_examples"))
  .settings(
    libraryDependencies += scalaTest,
    libraryDependencies += scalaCheck % Test,
    libraryDependencies += "org.typelevel" %% "cats-effect" % "1.3.0",
    scalacOptions := scalacOptions.value
      .filterNot(_ == "-Xfatal-warnings") 
      ++ Seq("-Ywarn-unused:-implicits,-locals,-privates")
    ,
    testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-l", "FailureExamples"),
  )


lazy val akkaVersion = "2.6.0-M1"

lazy val akka_examples = (project in file("akka_examples"))
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
      .filterNot(_ == "-Xfatal-warnings")
    ,
  )


// -------------------- Demo App

val circeVersion = "0.10.0"
val circeLibs = Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)


lazy val demo_app_model_dtos = (project in file("demo-app.model.dtos"))
  .settings(
    name := "demo-app.model.dtos",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += scalaCheck % Test,
    libraryDependencies ++= circeLibs,
  )


val akkaHttpVersion = "10.1.8"
val akkaDeps = Seq(
  "com.typesafe.akka" %% "akka-stream"          % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed"     % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,

  "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % Test,
)


lazy val demo_app_model = (project in file("demo-app.model"))
  .settings(
    name := "demo-app.model",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += scalaCheck % Test,
    libraryDependencies ++= akkaDeps
  )
  .dependsOn(demo_app_model_dtos)


// See: https://www.codemunity.io/tutorials/dockerising-akka-http/

lazy val demo_app = (project in file("demo-app"))
  .enablePlugins(JavaAppPackaging, AshScriptPlugin)
  .settings(
    name := "demo-app",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += scalaCheck % Test,
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
      "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % Test,
    ),
    mainClass in Compile := Some("demo_app.rest_server.DemoAppMain"),
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
