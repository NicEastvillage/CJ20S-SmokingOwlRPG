package eastvillage.cj20s.game.dungeon

enum class Direction {
    NORTH, SOUTH, WEST, EAST;

    fun toVec(): Vec2i {
        return when (this) {
            NORTH -> Vec2i(0, -1)
            SOUTH -> Vec2i(0, 1)
            WEST -> Vec2i(-1, 0)
            EAST -> Vec2i(1, 0)
        }
    }

    override fun toString(): String {
        return when (this) {
            NORTH -> "north"
            SOUTH -> "south"
            WEST -> "west"
            EAST -> "east"
        }
    }
}