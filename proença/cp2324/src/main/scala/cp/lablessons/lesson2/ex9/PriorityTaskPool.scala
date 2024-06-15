package cp.lablessons
package lesson2.ex9

import scala.collection.mutable.PriorityQueue
import scala.collection.mutable.ArrayBuffer

case class Task(priority: Int, body: ()=> Unit)

// Queue that stores and runs the tasks
// The higher the number of priority the higher the priority
class PriorityTaskPool(num_workers: Int){

  // Implicit - it will use lock implementation, where a order function is needed
  // _.priority - By default, the higher the int the more priority
  implicit val ord: Ordering[Task] = Ordering.by(_.priority)

  // Set of tasks with priority
  private val tasks = PriorityQueue[Task]()

  // List of Worker threads
  var worker_threads_buffer: ArrayBuffer[Thread] = new ArrayBuffer[Thread]()

  // create a specific lock instead of using the this.
  private val lock = new Object

  // Worker thread that goes over the tasks | started when the class is instantiatied
  for (i <- 1 to num_workers) {
    val worker: Thread = new Thread {
      def consume_task(): Option[Task] = lock.synchronized {
        while (!tasks.nonEmpty) { lock.wait() }

        if (tasks.nonEmpty) {
          return Some(tasks.dequeue())
        } else {
          return None
        }
      }

      override def run() = {
        while (true) {
          consume_task() match {
            case Some(task) => task.body()
            case None => // do nothing
          }
        }
      }
    }

    //  worker.setDaemon(true)
    worker.start()
    worker_threads_buffer += worker
  }

  def asynchronous(priority: Int)(task: =>Unit): Unit = {
    lock.synchronized {
      tasks.enqueue(new Task(priority, () => task))
      lock.notifyAll()
    }
  }
}

