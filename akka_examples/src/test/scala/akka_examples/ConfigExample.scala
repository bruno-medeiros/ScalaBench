package akka_examples

import akka_examples.common.AkkaExample
import com.typesafe.config.{Config, ConfigFactory}
//import akka_examples.SuperviseExample.system


// TODO: try out more
class ConfigExample extends AkkaExample {

  "Config example" in {

    val config: Config = ConfigFactory.load()

    println(s"${config.root().render()}")

  }
}