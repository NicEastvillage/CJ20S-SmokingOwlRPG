package eastvillage.cj20s.game

class Character(
        val owner: Player,
        val realname: String,
        val pcClass: PCClass
) : Targetable {
    override val isDead: Boolean get() = health.isDead
    override val longname: String get() = "$realname the $pcClass"
    override val shortName: String get() = realname

    override val health: Health = Health(when (pcClass) {
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