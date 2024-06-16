package cp.lablessons
package lesson3.ex1

import scala.concurrent.ExecutionContext

class PiggybackContext extends ExecutionContext {
  // Implement my self the ExecutionContext, for that i need:
  // execute()
  // reportFailure()

  @Override
  def execute(runnable: Runnable): Unit = runnable.run()

  @Override
  def reportFailure(cause: Throwable): Unit = throw cause
}

