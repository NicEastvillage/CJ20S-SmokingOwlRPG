package eastvillage.cj20s.game.dungeon

import eastvillage.cj20s.Emoji
import eastvillage.cj20s.EncounterManager
import eastvillage.cj20s.Inventory
import eastvillage.cj20s.game.Encounter
import eastvillage.cj20s.game.Monster

class Dungeon(
        val width: Int,
        val height: Int,
        private val tiles: MutableList<MutableList<Tile?>>,
        var partyPos: Vec2i
) {
    companion object {
        fun create(layout: DungeonLayout): Dungeon {
            var partyPos: Vec2i? = null
            var x = 0
            var y = 0
            val tiles = mutableListOf<MutableList<Tile?>>()
            tiles.add(mutableListOf())
            for (char in layout.layout) {
                if (char == '\n') {
                    y++
                    tiles.add(mutableListOf())
                    x = 0
                } else if (char == 'w') {
                    partyPos = Vec2i(x, y)
                    tiles[y].add(null)
                    x++
                } else {
                    tiles[y].add(when (char) {
                        '#' -> Tile.WALL
                        'x' -> Tile.MONSTER
                        'X' -> Tile.BOSS
                        'D' -> Tile.DOOR
                        'K' -> Tile.KEY
                        'C' -> Tile.CHEST
                        else -> null
                    })
                    x++
                }
            }
            return Dungeon(layout.width, layout.height, tiles, partyPos!!)
        }
    }

    operator fun get(pos: Vec2i): Tile? = tiles[pos.y][pos.x]
    operator fun set(pos: Vec2i, tile: Tile?) {
        tiles[pos.y][pos.x] = tile
    }

    operator fun contains(pos: Vec2i): Boolean = pos.x in 0 until width && pos.y in 0 until height

    override fun toString(): String {
        val sb = StringBuilder(width * height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                sb.append(tiles[y][x]?.toString() ?: " ")
            }
            sb.append('\n')
        }
        return sb.toString()
    }

    fun isComplete(): Boolean {
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (this[Vec2i(x, y)] in listOf(Tile.CHEST, Tile.MONSTER, Tile.BOSS)) return false
            }
        }
        return true
    }

    fun asEmotes(): String {
        val (px, py) = partyPos
        val sb = StringBuilder(width * height * 4)
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (px == x && py == y) {
                    sb.append(Emoji.PARTY) // Party's location
                } else {
                    sb.append(tiles[y][x]?.toEmote() ?: Emoji.FLOOR)
                }
            }
            sb.append('\n')
        }
        return sb.toString()
    }

    /**
     * Returns true if the move was successful
     */
    fun move(direction: Direction): MoveOutcome {
        val newPos = partyPos + direction.toVec()
        if (newPos in this) {
            if (this[newPos] == Tile.WALL) return MoveOutcome.WALL
            if (this[newPos] == Tile.DOOR && Inventory.keys == 0) return MoveOutcome.NEED_KEY
            if (this[newPos] == Tile.DOOR && Inventory.keys > 0) {
                Inventory.keys--
                this[newPos] = null
                partyPos = newPos
                return MoveOutcome.OPEN_DOOR
            }
            if (this[newPos] == Tile.KEY) {
                Inventory.keys++
                this[newPos] = null
                partyPos = newPos
                return MoveOutcome.FINDS_KEY
            }
            if (this[newPos] == Tile.MONSTER || this[newPos] == Tile.BOSS) {
                EncounterManager.encounter = Encounter(
                        if (this[newPos] == Tile.MONSTER) Monster.randomMonster()
                        else Monster.randomBoss()
                )
                partyPos = newPos
                this[newPos] = null
                return MoveOutcome.ENCOUNTER
            }
            if (this[newPos] == Tile.CHEST) {
                partyPos = newPos
                this[newPos] = null
                return MoveOutcome.OPEN_CHEST
            }

            partyPos = newPos
            return MoveOutcome.SUCCESS
        }
        return MoveOutcome.WALL
    }
}

class DungeonLayout(val layout: String, val width: Int, val height: Int)

val dungeons = listOf(
        DungeonLayout("""
            C# x #C
               #  X
            ##D##  
            Cx  ###
            # # # K
            w   x  
        """.trimIndent(), 7, 6),
        DungeonLayout("""
            xK#CKX  
             ##### 
            w   D C
            ##  #  
            K#x###x
            CD #C  
        """.trimIndent(), 7, 6),
        DungeonLayout("""
             C#w DxK
             xD  ## 
            x## xC#C
             K#K##CX
        """.trimIndent(), 8, 4),
        DungeonLayout("""
              C#K 
            #x##  
               D#x
            C #   
            # # # 
             X#x w
            C#C ##
        """.trimIndent(), 6, 7)
)