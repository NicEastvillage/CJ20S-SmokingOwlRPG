package eastvillage.cj20s.game

class Character(
        val owner: Player,
        val pcname: String,
        val pcClass: PCClass
) {
    val isDead: Boolean get() = health.isDead

    val health: Health = Health(when (pcClass) {
        PCClass.WIZARD -> 18
        PCClass.SORCERER -> 19
        PCClass.PRIEST -> 20
        PCClass.WARLOCK -> 16
    })

    val spells: MutableList<Attack> = mutableListOf(
            when (pcClass) {
                PCClass.WIZARD -> Fireball
                PCClass.SORCERER -> ArcaneBolt
                PCClass.PRIEST -> MinorRestoration
                PCClass.WARLOCK -> DrainLife
            }
    )
}