package akka_examples

import com.typesafe.config.{Config, ConfigFactory}
//import akka_examples.SuperviseExample.system


// TODO: try out more
class ConfigExample extends AkkaTest {

  "Config example" in {

    val config: Config = ConfigFactory.load()

    println(s"${config.root().render()}")

  }
}