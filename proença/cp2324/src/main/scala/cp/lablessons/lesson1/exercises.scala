package cp.lablessons.lesson1

object Exercices extends App {
  def compose[A, B, C](g: B => C, f: A => B): A => C =
    (a: A) => g(f(a))

  val result_compose = compose((b:Int) => b.toString, (a: Int) => a+1)(34)
  println(s"result_compose: $result_compose")

  def fuse1[A, B](a: Option[A], b: Option[B]): Option[(A, B)] =
    // Patern matching
  (a, b) match {
    case (Some(x),Some(z)) => Some((x,z))
    case _ => None
  }

  def fuse2[A, B](a: Option[A], b: Option[B]): Option[(A, B)] = {
    for (xa <- a; xb <- b) yield (xa, xb)
  }

  def check[T](xs: Seq[T])(pred: T => Boolean): Boolean = {
    try {
      for (x <- xs) {
        if (!pred(x)) {
          return false
        }
      }
      return true
    } catch {
      case _: Exception => false
    }
  }

  val result_check: Boolean = check(1 until 10)(40 / _  > 0)
  println(s"result_check: $result_check")
}

