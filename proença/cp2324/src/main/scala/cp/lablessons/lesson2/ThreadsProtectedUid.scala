package cp.lablessons
package lesson2

object ThreadsProtectedUid {
  var uidCount: Long = 0L
  def getUniqueId(): Long = {
    uidCount.synchronized {
      uidCount += 1
      uidCount
    }
  }
}

