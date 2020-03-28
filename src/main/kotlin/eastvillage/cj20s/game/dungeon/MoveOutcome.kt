package eastvillage.cj20s.game.dungeon

enum class MoveOutcome {
    SUCCESS,
    FINDS_KEY,
    WALL,
    NEED_KEY,
    OPEN_DOOR,
    ENCOUNTER,
    OPEN_CHEST
}