package cp.lablessons
package lesson2.ex10

object Ex10 extends App {
  var tasks_pool: PriorityTaskPool = new PriorityTaskPool(4, 100)

  for (i <- 1 to 100) {
    tasks_pool.asynchronous(i) {
      log(s"task with prioeiry ${i}")
      if (i == 45) tasks_pool.shutdown()
    }
  }
}

