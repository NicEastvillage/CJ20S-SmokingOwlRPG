package eastvillage.cj20s.game

import java.util.*

class Frontline(val maxSize: Int = 4) {

    private val queue: LinkedList<Character> = LinkedList()

    private fun moveToFront(character: Character) {
        queue.remove(character)
        queue.addFirst(character)
        if (queue.size >= maxSize) {
            queue.removeLast()
        }
    }

    val all: List<Character> get() = queue

    operator fun get(index: Int): Character? = if (index in 0 until queue.size) queue[index] else null

    fun isEmpty(): Boolean = queue.isEmpty()
}