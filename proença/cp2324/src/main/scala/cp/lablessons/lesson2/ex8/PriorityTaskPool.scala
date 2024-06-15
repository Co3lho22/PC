package cp.lablessons
package lesson2.ex8

import scala.collection.mutable.PriorityQueue

case  class Task(priority: Int, body: ()=> Unit)

// Queue that stores and runs the tasks
// The higher the number of priority the higher the priority
class PriorityTaskPool {

  // Implicit - it will use this implementation, where a order function is needed
  // _.priority - By default, the higher the int the more priority
  implicit val ord: Ordering[Task] = Ordering.by(_.priority)

  // Set of tasks with priority
  private val tasks = PriorityQueue[Task]()

  // Worker thread that goes over the tasks | started when the class is instantiatied
  private val worker: Thread = new Thread {
    def consume_task(): Option[Task] = this.synchronized {
      if (tasks.nonEmpty) Some(tasks.dequeue()) else None
    }

    override def run() = {
      while (true) {
        consume_task() match {
          case None => // do nothing
          case Some(task) => task.body()
        }
      }
    }
  }

  worker.setDaemon(true)
  worker.start()

  // Need to implement:
  def asynchronous(priority: Int)(task: =>Unit): Unit = {
    this.synchronized {
      tasks.enqueue(new Task(priority, () => task))
    }
  }
}

