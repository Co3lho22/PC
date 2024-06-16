package cp.lablessons
package lesson3.ex2

object Exercise2 extends App {
  val treiberStack: TreiberStack[Int] = new TreiberStack[Int]()
  treiberStack.push(1)
  val head: Option[Int] = treiberStack.pop()
  log(s"${head}")
}
