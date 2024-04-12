package cp.lablessons.lesson1

object Exercices extends App {
  def compose[A, B, C](g: B => C, f: A => B): A => C =
    (a: A) => g(f(a))

  println(
    compose((b:Int) => b.toString, (a: Int) => a+1)(34)
  )

  def fuse1[A, B](a: Option[A], b: Option[B]): Option[(A, B)] =
    // Patern matching
  (a, b) match {
    case (Some(x),Some(z)) => Some((x,z))
    case _ => None
  }

  def fuse2[A, B](a: Option[A], b: Option[B]): Option[(A, B)] =
    // For
  for (xa <- a; xb <- b) yield (xa, xb)
}

