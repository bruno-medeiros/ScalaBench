package examples

import scala.util.Using
import java.io.{FileReader, FileWriter}

object LoanPattern {

  Using.resources(
    new FileReader("input.txt"),
    new FileWriter("output.txt")) { (reader, writer) =>
    ???
  }

}
