package eastvillage.cj20s.game

class Encounter(
        val monster: Monster
) {
    val frontline: Frontline = Frontline()
    var queuedAttack: MonsterAttack = monster.attacks.random()

    fun resolveAttack(): String {
        val desc = queuedAttack.perform(monster, frontline)
        queuedAttack = monster.attacks.random()
        return desc
    }

    fun toStatusString(): String {
        val intention = when (queuedAttack.type) {
            MonsterAttackType.ATTACK -> "An attack"
            MonsterAttackType.DEFEND -> "A defensive move"
        }

        val desc = StringBuilder("""
            **Enemy:**
            ${monster.longname.capitalize()}, ${monster.health.asEmojis()}
            Intents to do: $intention
            
            **Your party's frontline:**
            
        """.trimIndent())

        if (frontline.isEmpty())
            desc.append("empty")
        else for (character in frontline.all) {
            desc.append("${character.longname} ${character.health.asEmojis()}\n")
        }

        return desc.toString()
    }
}