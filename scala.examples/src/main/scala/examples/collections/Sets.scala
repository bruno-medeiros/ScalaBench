package examples.collections

private object Sets {

  // Immutable sets:

  val setA: Set[Char] = Set('a','b')
  val setA_StringAppended: String = Set + "c" // + plus comes from any2stringadd

  val setA2: Set[Int] = Set(1, 2) + (3, 4)

  val setB: Set[Char] = setA ++ Set('c', 'd') // Fails because it becomes a

  // TODO: more, check perforamnce characteristics

  {
    // TODO:
    var setA = Set(1, 2)
    setA += 44
  }

}