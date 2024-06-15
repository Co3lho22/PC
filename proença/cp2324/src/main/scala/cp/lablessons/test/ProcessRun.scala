package cp.lablessons
package test

import scala.sys.process._

object ProcessRun extends App {
  val command = "ls"
  val exitcode = command.! // run process ( with side effects )
  log(s"command exited with status $exitcode ")

}

