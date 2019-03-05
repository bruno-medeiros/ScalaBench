package examples.matching

object ExtractorObjects extends App {

  object MyExtractor {
    def unapply(arg: String): Option[(String, Int)] = {
      if (arg.length > 4) {
        Some(arg.substring(0, 4), arg.length)
      } else {
        None
      }
    }
  }
  // Alternative syntax, more concise:
  object MyExtractor2 {
    def unapply(arg: String): Option[(String, Int)] = arg match {
      case _ if arg.length > 4 => Some(arg.substring(0, 4), arg.length)
      case _ => None
    }
  }

  {
    val MyExtractor2(a, b) = "ABCDEFG"
    assert((a, b) == ("ABCD", 7))

    // This is deprecated (scala/bug#6675)
//    val MyExtractor2(extracted) = "ABCDEFG"
//    assert(extracted == ("ABCD", 7))
  }

  {
    val MyExtractor2(extractedA, extractedB) = "ABCDEFG"
    assert(extractedA == "ABCD" && extractedB == 7)
  }
  println("MyExtractor done")


  {
    // :: Is a case class for List
    assert(::(1, ::(2, Nil)) == List(1, 2))

    val a :: b = List(1, 2)
    assert(b == List(2))
    println(s"ListExtractor: $a  $b")
  }


  object ComplexExtractor {
    def unapplySeq(arg: String) : Option[Seq[String]] = {
      if (arg.length > 5) {
        Some(arg.split("-").toVector)
      } else {
        None
      }
    }
  }
  {
    val ComplexExtractor(a, b, c) = "AAAA-bb-c"
    println(s"ComplexExtractor: $a  $b  $c")
  }
}
