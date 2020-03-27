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
}