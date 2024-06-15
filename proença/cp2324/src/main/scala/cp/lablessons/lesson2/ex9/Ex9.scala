package cp.lablessons
package lesson2.ex9

object Ex9 extends App {
  var tasks_pool: PriorityTaskPool = new PriorityTaskPool(4)

  for (i <- 1 to 100) {
    tasks_pool.asynchronous(i) {
      log(s"task with prioeiry ${i}")
    }
  }
}

