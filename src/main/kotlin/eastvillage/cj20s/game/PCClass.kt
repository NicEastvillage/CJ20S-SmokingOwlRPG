package eastvillage.cj20s.game

enum class PCClass {
    WIZARD, SORCERER, PRIEST, WARLOCK;

    override fun toString(): String {
        return when (this) {
            WIZARD -> "wizard"
            SORCERER -> "sorcerer"
            PRIEST -> "priest"
            WARLOCK -> "warlock"
        }
    }

    fun toId(): Long {
        return when (this) {
            WIZARD -> 693736836455071805
            SORCERER -> 693736912942268417
            PRIEST -> 693736885595406376
            WARLOCK -> 693736780083626004
        }
    }
}