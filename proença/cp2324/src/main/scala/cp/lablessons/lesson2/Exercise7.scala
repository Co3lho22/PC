package cp.lablessons
package lesson2

object Exercise7 extends App {
  class Account ( val name : String , var money : Int) {
    val uid = ThreadsProtectedUid.getUniqueId()
  }

  def send(a1: Account, a2: Account, money: Int): Unit = {
    def ajust() {
      a1.money -= money
      a2.money += money
    }
    if (a1.uid > a2.uid) a1.synchronized{ a2.synchronized{ ajust() }}
    else a2.synchronized{ a1.synchronized{ ajust() }}

  }

  def sendAll(accounts: Set[Account], target: Account): Unit = {
    for (account <- accounts) {
      send(account, target, account.money)
    }
  }

  // Testing

  var a1: Account = new Account("john", 3000)
  var a2: Account = new Account("diogo", 2500)
  var a3: Account = new Account("silva", 4000)
  var a4: Account = new Account("ruben", 200)
  val set = scala.collection.immutable.Set[Account](a1, a2, a3)
  for (account <- set) {
      log(s"${account.name}: ${account.money}")
  }

  sendAll(set, a4)
  for (account <- set) {
      log(s"${account.name}: ${account.money}")
  }

  log(s"${a4.name}: ${a4.money}")
}

