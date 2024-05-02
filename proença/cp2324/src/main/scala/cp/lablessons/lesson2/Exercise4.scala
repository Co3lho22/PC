package cp.lablessons
package lesson2  // Because this code is in the same package has the SyncVar
// class i can use it in here


// Busy-waiting is to use a loop that constantly checks for a condiction
object Exercise4 extends App {
  var obj_sync: SyncVar[Int] = new SyncVar[Int]

  var producer_value: Int = -1
  var producer: Thread = thread {
    while (producer_value < 15) {
      if (obj_sync.isEmpty()) {
        obj_sync.synchronized {
          producer_value = producer_value + 1
          obj_sync.put(producer_value)
        }
        // log(s"producer_value: $producer_value")
      }
    }
  }

  var consumer_value: Int = -1
  var consumer: Thread = thread {
    while (consumer_value < 15) {
      if (obj_sync.nonEmpty()) {
        obj_sync.synchronized{
          consumer_value = obj_sync.get()
        }
        log(s"consumer_value: $consumer_value")
      }
    }
  }
  producer.join()
  consumer.join()
}

