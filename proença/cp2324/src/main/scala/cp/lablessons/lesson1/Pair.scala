// class Pair[A , B]( val fst: A , val snd: B){
//   def +(other: Pair): Pair = {
//     (this.fst, this.snd, other.fst, other.snd) match {
//       case (a1: Int, a2: Int, b1: Int, b2: Int) => Pair(a1 + b1, a2 + b2)
//       case _ => throw new IllegalArgumentException("Unsupported type for addition")
//     }
//   }
//
//   val pair1: Pair[Int, Int] = new Pair(2, 4)
//   val pair2: Pair[Int, Int] = new Pair(8, 8)
//   val sum_pair: Pair[Int, Int] = pair1 + pair2
//   println(s"sum_pair: (${sum_pair.fst}, ${sum_pair.snd})")
// }
