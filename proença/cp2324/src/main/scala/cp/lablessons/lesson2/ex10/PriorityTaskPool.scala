package cp.lablessons
package lesson2.ex10

import scala.collection.mutable.PriorityQueue
import scala.collection.mutable.ArrayBuffer
import java.util.concurrent.atomic.AtomicBoolean

case class Task(priority: Int, body: ()=> Unit)

// Queue that stores and runs the tasks
// The higher the number of priority the higher the priority
class PriorityTaskPool(num_workers: Int, important: Int){
  // Implicit - it will use lock implementation, where a order function is needed
  // _.priority - By default, the higher the int the more priority
  implicit val ord: Ordering[Task] = Ordering.by(_.priority)
  // Flag for the shutdown
  var shutdown_mode: AtomicBoolean = new AtomicBoolean(false)
  // Set of tasks with priority
  private val tasks = PriorityQueue[Task]()
  // create a specific lock instead of using the this.
  private val lock = new Object


  // Worker thread that goes over the tasks | started when the class is instantiatied
  for (i <- 1 to num_workers) {
    val worker: Thread = new Thread {
      // Gets the task from queue/waits for one
      def consume_task(): Option[Task] = lock.synchronized {
        while (!tasks.nonEmpty && !shutdown_mode.get()) { lock.wait() }

        if (tasks.nonEmpty) {
          return Some(tasks.dequeue())
        } else {
          return None
        }
      }

      // Code that the thread starts running/ like a main
      override def run() = {
        var continue_thread: Boolean = true
        while (continue_thread) {
          consume_task() match {
            case Some(task) => {
              // To check if needs to run the task or shutdown the thread
              if (!shutdown_mode.get() || (task.priority >= important)) task.body()
              else continue_thread = false
            }
            case None => if (shutdown_mode.get()) continue_thread = false
          }
        }
      }
    }

    //  worker.setDaemon(true)
    worker.start()
  }

  // Add tasks to the PriorityQueue
  def asynchronous(priority: Int)(task: =>Unit): Unit = {
    lock.synchronized {
      tasks.enqueue(new Task(priority, () => task))
      lock.notifyAll()
    }
  }

  // Forces the worker threads to shutdown if the highest task priority < importance
  def shutdown(): Unit = {
    shutdown_mode.set(true)
  }
}

