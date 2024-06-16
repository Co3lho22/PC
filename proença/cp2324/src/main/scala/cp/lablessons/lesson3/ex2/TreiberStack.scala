package cp.lablessons
package lesson3.ex2

import scala.collection.mutable.Stack
import java.util.concurrent.atomic.AtomicReference
import scala.annotation.tailrec

// Each element of the stack is a instance of Node
case class Node[T] (value: T, lastNode: Node[T])

// To avoid the ABA problem
case class VersioningNode[T] (node: Node[T], version: Long)

// The node at the bottom of the stak as lastNode as null
class TreiberStack[T] {
  // This is the node at the top of the stack | Inicial set to null
  private var head: VersioningNode[T] = new VersioningNode[T](null, 0L)
  private var headRef: AtomicReference[VersioningNode[T]] = new AtomicReference[VersioningNode[T]](head)

  @tailrec
  final def push(x: T): Unit = {
    val oldHead: VersioningNode[T] = headRef.get()
    val newNode = new Node[T](x, oldHead.node)
    val nextVersion: Long = oldHead.version + 1
    val newHead = new VersioningNode[T](newNode, nextVersion)

    // Tries to insert the value
    if (!headRef.compareAndSet(oldHead, newHead)) push(x)
  }

  @tailrec
  final def pop(): Option[T] = {
    val oldHead: VersioningNode[T] = headRef.get()
    // Handle empty stack
    if (oldHead.node == null) return None

    val newVersion: Long = oldHead.version + 1
    val newHead: VersioningNode[T] = new VersioningNode[T](oldHead.node.lastNode, newVersion)
    if (headRef.compareAndSet(oldHead, newHead)) return Some(oldHead.node.value)
    else pop()
  }
}

