ThisBuild / organization := "com.example"
ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.7"

lazy val root = (project in file("."))
  .aggregate()


lazy val scala_examples = (project in file("scala.examples"))
  .settings(
//    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % "test"
    // other settings
  ) 

lazy val scala_examples2 = (project in file("scala.examples2"))
  .dependsOn(scala_examples % "test -> test")
  .settings(
    // other settings
  )