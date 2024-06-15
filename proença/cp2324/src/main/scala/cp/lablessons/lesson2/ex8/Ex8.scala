package cp.lablessons
package lesson2.ex8

object Ex8 extends App {
  val tasks_pool: PriorityTaskPool = new PriorityTaskPool()

  // Tasks
  tasks_pool.asynchronous(1) {
    log("task with prioeiry 1")
  }
  tasks_pool.asynchronous(3) {
    log("task with prioeiry 3")
  }

  tasks_pool.asynchronous(12) {
    log("task with prioeiry 12")
  }

  tasks_pool.asynchronous(34) {
    log("task with prioeiry 34")
  }

  tasks_pool.asynchronous(2) {
    log("task with prioeiry 2")
  }

  tasks_pool.asynchronous(4) {
    log("task with prioeiry 4")
  }


}
