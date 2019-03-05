package examples.libraries

import org.scalactic._
import TypeCheckedTripleEquals._

object ScalacticExample {


  def cmp(a: Int, b: Long): Int = {
    import TripleEquals._
    if (a === b) 0       // This line won't compile
    else if (a < b) -1
    else 1
  }

  def cmp(s: String, t: String): Int = {
    if (s === s) 0
    else if (s < t) -1
    else 1
  }
}