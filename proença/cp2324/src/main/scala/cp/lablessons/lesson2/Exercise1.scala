package cp.lablessons // This is to make sure i can use the functions defined
                      // in the package object
package lesson2

object Exercise1 extends App {
  def parallel[A, B](a: =>A, b: =>B): (A, B) = {
    var gja: Option[A] = None
    var gjb: Option[B] = None

    val fa = thread{
      gja = Some(a)
    }
    val fb = thread{
      gjb = Some(b)
    }
    fa.join(); fb.join(); // Waits for the thread fa and fb to finish before going to the next code
    (gja.get, gjb.get)
  }
  parallel(1+2, { log(s"Some random comment"); 4 })
}

