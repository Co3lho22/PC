package cp.lablessons
package lesson3.ex1

object Exercise1 extends App {
  val runnable: Runnable = new Runnable {
    @Override
    def run(): Unit = {
      log("test")
    }
  }

  val myExecutionContext: PiggybackContext = new PiggybackContext()
  myExecutionContext.execute(runnable)
}
