package eastvillage.cj20s.game

import kotlin.random.Random

sealed class Spell(
        val name: String,
        val desc: String
)

sealed class TargetedSpell(
        name: String,
        desc: String,
        val perform: (Character, Targetable) -> String
) : Spell(name, desc)

sealed class UntargetedSpell(
        name: String,
        desc: String,
        val perform: (Character, Frontline, Monster) -> String
) : Spell(name, desc)

object Fireball : TargetedSpell("fireball", "Deal 6-13 damage to the target. 20% chance to miss.", { caster, target ->
    if (Random.nextFloat() >= 0.2) {
        val amount = Random.nextInt(9, 14)
        target.health.takeDamage(amount)
        "${target.longname.capitalize()} takes $amount points of damage in the explosion!"
    } else {
        "But you miss your target .."
    }
})

object Ignite : TargetedSpell("ignite", "Deal 4-6 damage to the target. And 4 more over 2 turns.", { caster, target ->
    val amount = Random.nextInt(6, 8)
    target.health.takeDamage(amount)
    target.burnCount += 2
    "${target.longname.capitalize()} takes $amount points of damage and is set on fire for 2 turns!"
})

object ArcaneBolt : TargetedSpell("arcanebolt", "Deal 8 damage to the target.", { caster, target ->
    val amount = 8
    target.health.takeDamage(amount)
    "The purple ball of energy strikes ${target.longname} and deals $amount damage."
})

object ManaSurge : UntargetedSpell("manasurge", "Deal 3 damage to the enemy for each character on the frontline", { caster, frontline, monster ->
    val amount = frontline.all.size * 3
    monster.health.takeDamage(amount)
    if (frontline.all.size > 2)
        "\"Lend me your power, friends\" ${caster.realname} says as they draws in the energy of their allies. A powerful blast of raw energy deals $amount damage to the ${monster.race}!"
    else
        "\"Lend me your power, friends\" ${caster.realname} says as they draws in the energy of their allies. A disappointing ball of raw energy hits ${monster.longname} and deals $amount damage."
})

object MinorRestoration : TargetedSpell("restoration", "Restore 6-10 of target's health", { caster, target ->
    val amount = Random.nextInt(6, 11)
    target.health.heal(amount)
    "${caster.shortName.capitalize()} calls upon the gods and restore $amount points of health for ${target.longname}."
})

object HealingNova : UntargetedSpell("healingnova", "Restore 2 health to all frontline characters and deal 2 damage to the enemy.", { caster, frontline, enemy ->
    val healed = frontline.all
    for (char in healed) char.health.heal(2)
    enemy.health.takeDamage(2)
    "A wave of light crosses the battlefield as ${caster.longname} heals 2 health points of ${healed.map { it.realname }.joinToString()}, and deals 2 damage to ${enemy.shortName}."
})

object DrainLife : TargetedSpell("drainlife", "Deal 4-7 damage to the target. Caster restore 2 health.", { caster, target ->
    val amount = Random.nextInt(4, 8)
    target.health.takeDamage(amount)
    caster.health.heal(2)
    "A spark of life leaves the body of ${target.longname}. ${target.shortName.capitalize()} takes $amount of damage and ${caster.shortName} restores 2 health."
})

object Decay : TargetedSpell("decay", "Deal 6-9 damage if the target is missing health, other 2 damage.", { caster, target ->
    val hadFullHealth = target.health.hasFullHealth()
    val amount = if (hadFullHealth) 2 else Random.nextInt(6, 10)
    target.health.takeDamage(amount)
    if (hadFullHealth) {
        "A necrotic energy surrounds ${target.shortName} and deals $amount damage. It would have been more effective if the target was damaged. Oh well."
    } else {
        "The wounds of ${target.shortName} rottens and burns. ${target.shortName.capitalize()} takes $amount damage. Ew."
    }
})
