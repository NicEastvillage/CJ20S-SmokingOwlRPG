package eastvillage.cj20s.game

import kotlin.random.Random

sealed class MonsterAttack(
        val name: String,
        val description: String,
        val type: MonsterAttackType,
        val perform: (Monster, Frontline) -> String
)

enum class MonsterAttackType {
    ATTACK,
    DEFEND
}

object Bite : MonsterAttack("bite", "Deal 4-7 damage to the front character.", MonsterAttackType.ATTACK, { monster, frontline ->
    val target = frontline[0]!!
    val amount = Random.nextInt(4, 8)
    target.health.takeDamage(amount)
    listOf(
            "The ${monster.race} bites ${target.shortName} dealing $amount damage. Looks like it did not like that.",
            "The ${monster.race}'s teeth sink into ${target.shortName}'s flesh dealing $amount damage."
    ).random()
})

object Swipe : MonsterAttack("swipe", "Deal 4 damage to the front character, and 3 damage to the second front character.", MonsterAttackType.ATTACK, { monster, frontline ->
    val fstTarget = frontline[0]!!
    fstTarget.health.takeDamage(4)
    val sndTarget = frontline[1]
    if (sndTarget != null) {
        sndTarget.health.takeDamage(3)
        "${monster.longname.capitalize()} swipes at the party dealing 4 damage to ${fstTarget.shortName} and 3 damage to ${sndTarget.shortName}"
    } else {
        listOf(
                "${monster.longname.capitalize()} swipes at the party dealing 4 damage to ${fstTarget.shortName}",
                "Sharp nails streak across the skin of ${fstTarget.longname}. The suffered lacerations cause 4 damage."
        ).random()
    }
})

object Claw : MonsterAttack("claw", "Deal 5 damage to the front character.", MonsterAttackType.ATTACK, { monster, frontline ->
    val target = frontline[0]!!
    val amount = 5
    target.health.takeDamage(amount)
    listOf(
            "${monster.longname.capitalize()}'s claw strikes ${target.longname} for $amount damage and opens a grim would.",
            "A quick claw flies across ${target.shortName}'s body, slashing open new wounds and dealing $amount damage."
    ).random()
})

object Pounce : MonsterAttack("pounce", "Deal 4 damage to the front character, or 7 if the monster have full health", MonsterAttackType.ATTACK, { monster, frontline ->
    val target = frontline[0]!!
    val amount = if (monster.health.hasFullHealth()) 7 else 4
    target.health.takeDamage(amount)
    if (monster.health.hasFullHealth())
        "${monster.longname.capitalize()} pounces ${target.shortName} with full force dealing $amount damage. ${target.shortName} loses their breath for a minute."
    else
        "${monster.longname.capitalize()} pounces ${target.shortName} dealing $amount damage. If you hadn't attacked ${monster.shortName} before, that attack would have been even stronger!"
})

object Smash : MonsterAttack("smash", "Deal 6-10 damage to the front character. Has 40% chance to miss.", MonsterAttackType.ATTACK, { monster, frontline ->
    val target = frontline[0]!!
    if (Random.nextFloat() >= 40) {
        val amount = Random.nextInt(6, 11)
        target.health.takeDamage(amount)
        "${monster.race.capitalize()}'s grotesque arm smashes ${target.longname}. The ground shakes and they take $amount damage."
    } else {
        "${target.shortName.capitalize()} manage to dodge the ${monster.race}'s smash attack avoiding a ton of damange. Close one!"
    }
})

object Regenerate : MonsterAttack("regerenate", "Regenerate 3-6 health.", MonsterAttackType.DEFEND, { monster, frontline ->
    val amount = Random.nextInt(3, 7)
    monster.health.heal(amount)
    listOf(
            "The ${monster.race} steps back and takes a deep breath as it regenerates $amount health points",
            "The natural abilities of the ${monster.race} allows it to regenerate $amount health.",
            "${monster.longname} falls back and lick their wounds. It restores $amount points of health"
    ).random()
})

object Slash : MonsterAttack("slash", "Deal 2 damage to the three front characters", MonsterAttackType.ATTACK, { monster, frontline ->
    val targets = mutableListOf<Character>()
    targets.add(frontline[0]!!)
    frontline[1]?.let { targets.add(it) }
    frontline[2]?.let { targets.add(it) }
    for (target in targets) {
        target.health.takeDamage(2)
    }
    listOf(
            "${monster.shortName.capitalize()} slashes into the party, dealing 2 damage to ${targets.map { it.realname }.joinToString()}."
    ).random()
})

object WildFlame : MonsterAttack("wildflame", "Deal 6-8 damage randomly split between the frontline characters", MonsterAttackType.ATTACK, { monster, frontline ->
    val amount = Random.nextInt(6, 9)
    val targets = frontline.all
    val split = mutableMapOf<Character, Int>()
    for (dmg in 0 until amount) {
        val target = targets.random()
        if (target !in split) split[target] = 0
        split[target] = split[target]!! + 1
    }
    for (character in targets) {
        if (character in split)
            character.health.takeDamage(split[character]!!)
    }
    "The ${monster.race} conjures a wild flame that deals ${split.map { (char, dmg) -> "$dmg damage to ${char.realname}" }.joinToString()}"
})
