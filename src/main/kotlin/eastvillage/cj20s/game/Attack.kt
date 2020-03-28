package eastvillage.cj20s.game

import kotlin.random.Random

sealed class Attack(
        val name: String,
        val desc: String
)

sealed class TargetedAttack(
        name: String,
        desc: String,
        val perform: (Character, Health) -> Unit
) : Attack(name, desc)

sealed class UntargetedAttack(
        name: String,
        desc: String,
        val perform: (Character) -> Unit
) : Attack(name, desc)

object Fireball : TargetedAttack("Fireball", "Deal 9-13 damage to the target. 20% chance to miss.", { caster, target ->
    if (Random.nextFloat() >= 0.2) {
        target.takeDamage(Random.nextInt(9, 14))
    }
})

object ArcaneBolt : TargetedAttack("ArcaneBolt", "Deal 8 damage to the target.", { caster, target ->
    target.takeDamage(8)
})

object MinorRestoration : TargetedAttack("MinorRestoration", "Restore 6-10 of target's health", { caster, target ->
    target.heal(Random.nextInt(6, 11))
})

object DrainLife : TargetedAttack("DrainLife", "Deal 4-7 damange to the target. Caster restore 2 health.", { caster, target ->
    target.takeDamage(Random.nextInt(4, 8))
    caster.health.heal(2)
})