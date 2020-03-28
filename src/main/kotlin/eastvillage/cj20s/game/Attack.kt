package eastvillage.cj20s.game

import kotlin.random.Random

sealed class Attack(
        val name: String,
        val desc: String
)

sealed class TargetedAttack(
        name: String,
        desc: String,
        val perform: (Character, Targetable) -> String
) : Attack(name, desc)

sealed class UntargetedAttack(
        name: String,
        desc: String,
        val perform: (Character) -> String
) : Attack(name, desc)

object Fireball : TargetedAttack("fireball", "Deal 9-13 damage to the target. 20% chance to miss.", { caster, target ->
    if (Random.nextFloat() >= 0.2) {
        val amount = Random.nextInt(9, 14)
        target.health.takeDamage(amount)
        "${target.longname.capitalize()} takes $amount of damage in the explosion!"
    } else {
        "But you miss your target .."
    }
})

object ArcaneBolt : TargetedAttack("arcanebolt", "Deal 8 damage to the target.", { caster, target ->
    val amount = 8
    target.health.takeDamage(amount)
    "The purple ball of energy strikes ${target.longname} and deals $amount damage."
})

object MinorRestoration : TargetedAttack("restoration", "Restore 6-10 of target's health", { caster, target ->
    val amount = Random.nextInt(6, 11)
    target.health.heal(amount)
    "${caster.shortName.capitalize()} calls upon the gods and restore $amount points of health for ${target.longname}."
})

object DrainLife : TargetedAttack("drainlife", "Deal 4-7 damage to the target. Caster restore 2 health.", { caster, target ->
    val amount = Random.nextInt(4, 8)
    target.health.takeDamage(amount)
    caster.health.heal(2)
    "A spark of life leaves the body of ${target.longname}. ${target.shortName.capitalize()} takes $amount of damage and ${caster.shortName} restores 2 health."
})
