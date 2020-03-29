package eastvillage.cj20s.game

class Character(
        val owner: Player,
        val realname: String,
        val pcClass: PCClass
) : Targetable {
    override val isDead: Boolean get() = health.isDead
    override val longname: String get() = "$realname the $pcClass"
    override val shortName: String get() = realname

    override var burnCount: Int = 0

    override val health: Health = Health(when (pcClass) {
        PCClass.WIZARD -> 18
        PCClass.SORCERER -> 19
        PCClass.PRIEST -> 20
        PCClass.WARLOCK -> 16
    })

    val spells: MutableList<Spell> = when (pcClass) {
                PCClass.WIZARD -> mutableListOf(Fireball, Ignite)
                PCClass.SORCERER -> mutableListOf(ArcaneBolt, ManaSurge)
                PCClass.PRIEST -> mutableListOf(MinorRestoration, HealingNova)
                PCClass.WARLOCK -> mutableListOf(DrainLife, Decay)
            }
}