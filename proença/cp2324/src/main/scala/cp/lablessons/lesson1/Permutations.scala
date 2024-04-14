package cp.lablessons.lesson1

object Permutations extends App {
  def removeFirst(chr: Char, x: String): String = {
    val index = x.indexOf(chr)
    index match {
      case -1 => {
        println(s"The char: $chr is not in $x")
        x
      }
      case _ => x.substring(0, index) + x.substring(index + 1)
    }
  }

  def permutations(x: String): Seq[String] = {
    if (x.length == 1) Seq(x)
    else {
      x.flatMap(char =>
          permutations(removeFirst(char, x)).map(char + _)
          ).distinct.sorted
    }
  }

  val result: Seq[String] = permutations("bcdd")
  for( str <- result){
    println(s"$str")
  }
}

