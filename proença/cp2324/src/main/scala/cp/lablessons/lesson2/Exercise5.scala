package cp.lablessons
package lesson2

object Exercise5 extends App {
  var obj_sync: SyncVar[Int] = new SyncVar[Int]

  val producer: Thread = thread {
    log(s"Producer Sleeping for 5 seconds")
    Thread.sleep(5000)
    obj_sync.putWait(4)
  }

  val consumer: Thread = thread {
    val obj_value: Int = obj_sync.getWait()
    log(s"obj_value: ${obj_value}")
  }

  producer.join()
  consumer.join()
}


