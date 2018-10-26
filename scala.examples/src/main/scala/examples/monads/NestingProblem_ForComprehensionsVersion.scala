package examples.monads

//noinspection ZeroIndexToHead
object NestingProblem_ForComprehensionsVersion extends App {

  println("checkSeq: " + checkSeq("abced"))

  def defCheck0(seq: String): Boolean = {
    if (seq.isEmpty) {
      true
    } else {
      val aIx = seq.indexOf('a')
      if (aIx == -1 || aIx % 2 == 0) {
        true
      } else {
        val bIx = seq.indexOf(aIx, 'b')
        if (bIx == -1 || bIx % 5 == 0) {
          true
        } else {
          aIx == bIx +1
        }
      }
    }
  }

  def checkSeq(seq: String): Boolean = {
    (for {
      _ <- Some(())
      if seq.isEmpty
      aIx = seq.indexOf('a')
      if aIx == -1 || aIx % 2 == 0
      bIx = seq.indexOf(aIx, 'b')
      if bIx == -1 || bIx % 5 == 0
    } yield aIx == bIx + 1)
      .getOrElse(true)
  }

}
