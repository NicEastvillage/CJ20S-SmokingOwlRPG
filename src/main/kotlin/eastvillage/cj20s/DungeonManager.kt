package eastvillage.cj20s

import eastvillage.cj20s.game.dungeon.Dungeon
import eastvillage.cj20s.game.dungeon.dungeons
import java.util.*

object DungeonManager {

    var queue = LinkedList(dungeons.shuffled())

    var dungeon = Dungeon.create(queue.removeFirst())

    fun beginNewDungeon() {
        dungeon = Dungeon.create(queue.removeFirst())
        if (queue.isEmpty()) {
            queue = LinkedList(dungeons.shuffled())
        }
    }
}