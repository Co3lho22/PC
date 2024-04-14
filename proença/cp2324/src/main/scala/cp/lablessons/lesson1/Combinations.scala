package cp.lablessons.lesson1

object Combinations extends App {
  def combinations(n: Int, xs: Seq[Int]): Iterator[Seq[Int]] = {
    if (n == 0) Iterator(Seq.empty)
    else xs match {
      case Seq() => Iterator.empty
      case head +: tail =>
        combinations(n - 1, tail).map(head +: _) ++ combinations(n, tail)
    }
  }
  val nums = Seq(1, 4, 9, 16)
  val comboLength = 4

  val result = combinations(comboLength, nums).toList

  println(s"Combinations of length $comboLength from $nums are:")
  result.foreach(combination => println(combination.mkString(", ")))
}

