package cp.lablessons
package lesson2

import scala.collection.mutable

object Exercise6_test extends App {
  val obj_syncqueue: SyncQueue[Int] = new SyncQueue[Int](4)
  log(s"isEmpty: ${obj_syncqueue.isEmpty()}")
  obj_syncqueue.put(1)
  obj_syncqueue.put(2)
  obj_syncqueue.put(3)
  obj_syncqueue.put(4)

  log(s"nonEmpty: ${obj_syncqueue.nonEmpty()}")

  log(s"${obj_syncqueue.get()}")
  log(s"${obj_syncqueue.get()}")
  log(s"${obj_syncqueue.get()}")
  log(s"${obj_syncqueue.get()}")

  log(s"isEmpty: ${obj_syncqueue.isEmpty()}")

  log(s"Testing the wait methods")
  val producer: Thread = thread {
    obj_syncqueue.putWait(3)
    log(s"Producer sleeping 2 seconds")
    Thread.sleep(2000)
    obj_syncqueue.putWait(4)
    obj_syncqueue.putWait(5)
  }

  val consumer: Thread = thread {
    var value: Int = obj_syncqueue.getWait()
    log(s"consumer value: $value")
    value = obj_syncqueue.getWait()
    log(s"consumer value: $value")
    value = obj_syncqueue.getWait()
    log(s"consumer value: $value")
  }
}

