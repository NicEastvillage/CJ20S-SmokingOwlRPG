package eastvillage.cj20s.game.dungeon

import eastvillage.cj20s.Emoji

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
            WALL -> Emoji.WALL
            MONSTER -> Emoji.MONSTER
            BOSS -> Emoji.BOSS
            DOOR -> Emoji.DOOR
            KEY -> Emoji.KEY
            CHEST -> Emoji.CHEST
        }
    }
}