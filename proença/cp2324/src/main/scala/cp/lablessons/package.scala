package cp

package object lablessons {
  def log(msg: String): Unit = {
    val threadType = if (Thread.currentThread().isDaemon) "Daemon" else "User"
    println (
      s"${Thread.currentThread.getName} ($threadType thread): $msg"
    )
  }

  def thread(body: =>Unit, daemon: Boolean = false): Thread = {
    val t = new Thread {
      override def run() = body
    }
    t.setDaemon(daemon)
    t.start()
    t
  }
}

