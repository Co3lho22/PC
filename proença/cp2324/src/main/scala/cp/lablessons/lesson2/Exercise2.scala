package cp.lablessons
package lesson2

object Exercise2 extends App {
  // Normal thread prevent the JVM from exit until they finish
  def periodically(duration: Long)(b: =>Unit): Unit = {
    thread {
      while (true) {
        b
        Thread.sleep(duration)
      }
    }
  }

  // Daemon thread are threads that do not prevent the JVM from exiting
  // When all user threads finish the JVM halts and the daemon thread are stoped
  def  periodically_daemon(duration: Long)(b: =>Unit): Unit = {
    thread({
      while (true) {
        b
        Thread.sleep(duration)
      }
    },
    true)
  }

  periodically(2000)({
    log("Running and waiting 2 seconds")
  })

  periodically_daemon(2000){
    log("Running a deamon and waiting 2 seconds")
  }
}

