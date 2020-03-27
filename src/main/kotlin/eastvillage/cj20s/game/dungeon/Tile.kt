package eastvillage.cj20s.game.dungeon

enum class Tile {
    WALL,
    MONSTER,
    BOSS,
    DOOR,
    KEY,
    CHEST;

    override fun toString(): String {
        return when (this) {
            WALL -> "#"
            MONSTER -> "x"
            BOSS -> "X"
            DOOR -> "D"
            KEY -> "K"
            CHEST -> "C"
        }
    }

    fun toEmote(): String {
        return when (this) {
            WALL -> "<:wall:693202949534646323>"
            MONSTER -> "<:monster:693202949496635475>"
            BOSS -> "<:boss:693202949656150046>"
            DOOR -> "<:door:693202949542903948>"
            KEY -> "<:key:693202949551423538>"
            CHEST -> "<:chest:693202949525995530>"
        }
    }
}