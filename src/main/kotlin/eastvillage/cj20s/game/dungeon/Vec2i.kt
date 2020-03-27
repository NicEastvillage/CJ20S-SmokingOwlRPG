package eastvillage.cj20s.game.dungeon

data class Vec2i(val x: Int, val y: Int) {

    operator fun plus(other: Vec2i): Vec2i = Vec2i(x + other.x, y + other.y)
    operator fun minus(other: Vec2i): Vec2i = Vec2i(x - other.x, y - other.y)

    override fun toString(): String {
        return "($x, $y)"
    }
}