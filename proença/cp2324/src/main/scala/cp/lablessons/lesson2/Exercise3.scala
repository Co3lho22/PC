package cp.lablessons
package lesson2

class SyncVar[T] {
  var obj: Option[T] = None

  def get(): T = {
    obj match {
      case Some(value) =>{
        obj = None
        value
      }
      case None => throw new RuntimeException("The object is empty.")
    }
  }

  def put(x: T): Unit = {
    obj match {
      case Some(_) => throw new RuntimeException("The object is not empty.")
      case None => obj = Some(x)
    }
  }

  def isEmpty(): Boolean = {
    obj == None
  }

  def nonEmpty(): Boolean = {
    obj != None
  }
}

object Exercise3 extends App {
  var obj_sync: SyncVar[Int] = new SyncVar[Int]

  // Test SyncVar
  try {
    obj_sync.put(2)
    println("Put 2 successfully.")
    val result: Int = obj_sync.get()
    println("Retrieved value: " + result)

    // Testing error handling
    obj_sync.put(3)  // This should throw an exception
  } catch {
    case e: RuntimeException => println(e.getMessage)  // Expected: "The object is not empty."
  }


  try {
    val newResult: Int = obj_sync.get()  // This should throw an exception since it's now empty
    println("Retrieved value after empty: " + newResult)
  } catch {
    case e: RuntimeException => println("Error: " + e.getMessage)  // Expected: "The object is empty."
  }

  try {
    obj_sync.put(4)  // Now this should succeed
    println("Put 4 successfully after empty.")
  } catch {
    case e: Exception => println("Unexpected error: " + e.getMessage)
  }
}

