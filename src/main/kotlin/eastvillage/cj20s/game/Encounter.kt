package eastvillage.cj20s.game

class Encounter(
        val monster: Monster
) {
    val frontline: Frontline = Frontline()
    val queuedAttack: String = "TODO"

    fun toStatusString(): String {
        val desc = StringBuilder("""
            Enemy:
            ${monster.longname.capitalize()}, ${monster.health.asEmojis()}
            
            Your party's frontline:
            
        """.trimIndent())

        if (frontline.isEmpty())
            desc.append("empty")
        else for (character in frontline.all) {
            desc.append("${character.longname} ${character.health.asEmojis()}\n")
        }

        return desc.toString()
    }
}