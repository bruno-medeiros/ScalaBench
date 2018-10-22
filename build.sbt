ThisBuild / organization := "com.example"
ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.7"

lazy val root = (project in file("."))
  .aggregate(scala_examples, scala2)


lazy val scala_examples = (project in file("scala.examples"))
  .settings(
    // other settings
  )

lazy val scala2 = (project in file("scala.examples2"))
  .dependsOn(scala_examples % "test -> test")
  .settings(
    // other settings
  )