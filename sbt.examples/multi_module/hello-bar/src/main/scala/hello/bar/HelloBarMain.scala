package hello.bar

import hello.lib.HelloLib

object HelloBarMain extends App {
  println(HelloLib.greeting() + " Bar!")
}