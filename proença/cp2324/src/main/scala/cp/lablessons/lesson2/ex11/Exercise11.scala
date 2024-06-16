package cp.lablessons
package lesson2.ex11

import java.util.concurrent.{ConcurrentHashMap, CountDownLatch}
import java.util.Map.Entry
import scala.util.Random

object Exercise11 extends App {
  // Here will also be the tests for exercise 13
  private var biMap: ConcurrentBiMap[Int, Int] = new ConcurrentBiMap[Int, Int]()
  val numThreads = 8
  val numPairs = 100

  // Function to insert key-value pairs
  def insertPairs(start: Int, end: Int, latch: CountDownLatch): Runnable = new Runnable {
    def run(): Unit = {
      for (i <- start until end) {
        biMap.put(i, i + 1)
      }
      latch.countDown()
    }
  }

  // Function to invert key-value pairs
  def invertPairs(latch: CountDownLatch): Runnable = new Runnable {
    def run(): Unit = {
      val it = biMap.iterator
      while (it.hasNext) {
        val (k, v) = it.next()
        biMap.replace(k, v, v, k)
      }
      latch.countDown()
    }
  }

  // Create and start threads to insert pairs
  val insertLatch = new CountDownLatch(numThreads)
  val insertThreads = for (i <- 0 until numThreads) yield {
    val start = i * numPairs
    val end = start + numPairs
    val thread = new Thread(insertPairs(start, end, insertLatch))
    thread.start()
    thread
  }

  // Wait for all insertion threads to complete
  insertLatch.await()

  // Create and start threads to invert pairs
  val invertLatch = new CountDownLatch(numThreads)
  val invertThreads = for (_ <- 0 until numThreads) yield {
    val thread = new Thread(invertPairs(invertLatch))
    thread.start()
    thread
  }

  // Wait for all inversion threads to complete
  invertLatch.await()

  // Print the first few elements to verify
  println("Some entries in the map after inversion:")
  biMap.iterator.take(10).foreach { case (k, v) => println(s"$k -> $v") }

  // Verify correctness
  var isCorrect = true
  val it = biMap.iterator
  while (it.hasNext) {
    val (k, v) = it.next()
    if (k != v - 1) {
      println(s"Error: $k -> $v")
      isCorrect = false
    }
  }
  if (isCorrect) {
    println("All entries are correctly inverted.")
  } else {
    println("There are errors in the inversion.")
  }
}

