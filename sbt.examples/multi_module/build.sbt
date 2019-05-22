// This is "bare-style"
ThisBuild / scalaVersion := "2.12.7"
ThisBuild / organization := "com.example"
ThisBuild / version      := "0.1.0-SNAPSHOT"


val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"


lazy val svcHelloFoo = (project in file("hello-foo"))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "Hello Foo",
    libraryDependencies ++= Seq(
      scalaTest % Test,
    ),
    mainClass in Compile := Some("hello.foo.HelloFooMain"),
  )

lazy val helloLib = (project in file("lib-hello"))
  .settings(
    libraryDependencies ++= Seq(
      scalaTest % Test,
    ),
  )

lazy val svcHelloBar = (project in file("hello-bar"))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "Hello Bar",
    libraryDependencies ++= Seq(
      scalaTest % Test,
    ),
    mainClass in Compile := Some("hello.bar.HelloBarMain"),
  )
  .dependsOn(helloLib)


lazy val root = project.in(file("."))
  .aggregate(svcHelloFoo, svcHelloBar)
