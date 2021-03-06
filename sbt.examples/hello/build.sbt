enablePlugins(JavaAppPackaging)

lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "Hello",
    libraryDependencies += scalaTest % Test,
    mainClass in Compile := Some("example.HelloMain2"),
  )
