package cp.lablessons
package lesson2

import scala.collection.mutable

class SyncQueue[T](n: Int) {
  var obj_queue: mutable.Queue[T] = mutable.Queue[T]()

  def get(): T = {
    if (obj_queue.size == 0) {
      throw new RuntimeException("The queue is empty")
    }
    obj_queue.dequeue()
  }

  def getWait(): T = {
    this.synchronized {
      while (obj_queue.size == 0) this.wait()

      val value: T = get()
      this.notify
      value
    }

  }

  def put(x: T): Unit = {
    if (obj_queue.size == n) {
      throw new RuntimeException(s"The queue is full (already $n elements)")
    }
    obj_queue.enqueue(x)
  }

  def putWait(x: T): Unit = {
    this.synchronized {
      while (obj_queue.size == n) this.wait()
      put(x)
      this.notify()
    }
  }

  def isEmpty(): Boolean = {
    obj_queue.size == 0
  }
  def nonEmpty(): Boolean = {
    obj_queue.size != 0
  }
}

