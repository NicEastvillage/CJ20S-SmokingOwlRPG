package eastvillage.cj20s.game.dungeon

import eastvillage.cj20s.Inventory

class Dungeon(
        val width: Int,
        val height: Int,
        private val tiles: MutableList<MutableList<Tile?>>,
        var partyPos: Vec2i
) {
    companion object {
        fun create(layout: String, width: Int, height: Int): Dungeon {
            var partyPos: Vec2i? = null
            var x = 0
            var y = 0
            val tiles = mutableListOf<MutableList<Tile?>>()
            tiles.add(mutableListOf())
            for (char in layout) {
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
            return Dungeon(width, height, tiles, partyPos!!)
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

    fun asEmotes(): String {
        val (px, py) = partyPos
        val sb = StringBuilder(width * height * 4)
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (px == x && py == y) {
                    sb.append("<:party:693209897726378116>") // Party's location
                } else {
                    sb.append(tiles[y][x]?.toEmote() ?: "<:floor:693202949492572220>")
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

            partyPos = newPos
            return MoveOutcome.SUCCESS
        }
        return MoveOutcome.WALL
    }
}

val dungeons = listOf(
        Dungeon.create("""
            C# x #X
               #  C
            ##D##  
            Cx  ###
            # # # K
            w   x  
        """.trimIndent(), 7, 6)
)