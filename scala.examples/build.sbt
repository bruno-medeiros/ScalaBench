//import Dependencies._

lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"

lazy val scala_examples = (project in file(".")).
  settings(
//    inThisBuild(List(
//      organization := "com.example",
//      scalaVersion := "2.12.6",
//      version      := "0.1.0-SNAPSHOT"
//    )),
    name := "scala.examples",
    libraryDependencies += scalaTest % Test
  )
