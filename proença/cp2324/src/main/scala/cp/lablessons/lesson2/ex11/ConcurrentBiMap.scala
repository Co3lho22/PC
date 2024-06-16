package cp.lablessons
package lesson2.ex11

import java.util.concurrent.ConcurrentHashMap // This is a concurrent collection
import java.util.Map.Entry


// To this code i need to be carefull with the writes and rewrite because of
// possible race condictions. No need to be carefull with the read operations

// Bidirectonal Map: Unique Keys and Unique Values
class ConcurrentBiMap[K, V] {
  private var keysToValuesMap: ConcurrentHashMap[K,V] = new ConcurrentHashMap[K, V]()
  private var valuesToKeysMap: ConcurrentHashMap[V,K] = new ConcurrentHashMap[V, K]()
  private var obj: Object = new Object()

  def put(k: K, v: V): Option[(K, V)] = obj.synchronized {
    // Only necessary to check one
    if (keysToValuesMap.containsKey(k)) None
    else {
      keysToValuesMap.put(k, v)
      valuesToKeysMap.put(v, k)
      Some((k,v))
    }
  }

  def removeKey(k: K): Option[V] = obj.synchronized {
    val v: V = keysToValuesMap.get(k)
    if ( v != null) {
      valuesToKeysMap.remove(v)
      keysToValuesMap.remove(k)
      Some(v)
    } else None
  }


  def removeValue(v: V): Option[K] = obj.synchronized {
    val k: K = valuesToKeysMap.get(v)
    if ( k != null) {
      valuesToKeysMap.remove(v)
      keysToValuesMap.remove(k)
      Some(k)
    } else None
  }

  def getValue(k: K): Option[V] = {
    val v: V = keysToValuesMap.get(k)
    if (v != null) Some(v)
    else None
  }

  def getKey(v: V): Option[K] = {
    val k: K = valuesToKeysMap.get(v)
    if (k != null) Some(k)
    else None
  }

  def size: Int = keysToValuesMap.size()

  // To build a iterator from scratch i necessary two methods:
  // hasNext()
  // next()
  def iterator: Iterator[(K, V)] = new Iterator[(K, V)] {
    private val entryIterator: java.util.Iterator[Entry[K, V]] =
      keysToValuesMap.entrySet().iterator()

    override def hasNext(): Boolean = entryIterator.hasNext()

    override def next(): (K, V) = {
      val map_element: Entry[K, V] = entryIterator.next()
      (map_element.getKey(), map_element.getValue())
    }
  }

  // Does not add if the old pair is not present
  def replace(ok: K, ov: V, nk: K, nv: V): Unit = obj.synchronized {
    if (removeKey(ok) != None ) put(nk, nv)
  }
}

