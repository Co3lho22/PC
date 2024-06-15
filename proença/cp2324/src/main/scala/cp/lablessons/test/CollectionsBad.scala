package cp.lablessons
package test

import scala.collection._
import java.util.concurrent.atomic.AtomicInteger

object CollectionsBad extends App {
  val bf = mutable.ArrayBuffer[Int]()
  // var bfv2 = mutable.ArrayBuffer[AtomicInteger]()
  val bfv2 = mutable.ArrayBuffer[AtomicInteger]()

  def asyncAdd(numbers: Seq[Int], numbersv2: Seq[AtomicInteger]) =
    execute {
      // Mutable type do not work well with paralelism
      //      bf ++= numbers
      //      log(s"bf = $bf")

      // Alternatives

      // Mutable type + synchronized work well with paralelism
      bf.synchronized {
        bf ++= numbers
        log(s"bf = $bf")
      }

      // Imutable type with atomic values
      bfv2 ++= numbersv2
      log(s"bfv2 = $bfv2")

    }

  // Helper function to create a sequence of AtomicInteger
  def createAtomicSeq(range: Range): Seq[AtomicInteger] = {
    range.map(new AtomicInteger(_))
  }

  // Create sequences of AtomicInteger
  val atomicSeq1 = createAtomicSeq(0 until 5)
  val atomicSeq2 = createAtomicSeq(5 until 10)

  asyncAdd(0 until 5, atomicSeq1)
  asyncAdd(5 until 10, atomicSeq2)
  Thread.sleep(500)
}

