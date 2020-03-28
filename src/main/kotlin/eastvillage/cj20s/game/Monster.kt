package eastvillage.cj20s.game

import kotlin.random.Random

class Monster(
        val name: String?,
        val race: String,
        val health: Health,
        val isBoss: Boolean
) {
    val isDead: Boolean get() = health.isDead

    companion object {
        fun randomMonster(): Monster = Monster(null, monsterRaces.random(), Health(Random.nextInt(20, 26)), false)
        fun randomBoss(): Monster = Monster(bossNames.random(), bossRaces.random(), Health(Random.nextInt(40, 50)), true)
    }
}


val monsterRaces = listOf(
        "Goblin",
        "Rat",
        "Imp",
        "Wolf",
        "Bear",
        "Skeleton",
        "Zombie",
        "Demon",
        "Elemental",
        "DragonWelp",
        "Siren",
        "Panther",
        "Ogre",
        "Grimlin"
)

val bossRaces = listOf(
        "Dragon",
        "Manticore",
        "Lich",
        "Necromancer",
        "Golem",
        "Hydra",
        "Basilisk"
)

val bossNames = listOf(
        "Elith",
        "Torkal",
        "Qwen",
        "Talisfal",
        "Tensen",
        "Golia",
        "Mansahir",
        "Xenathar",
        "Bob",
        "Corona",
        "Daedarion",
        "Umiloron",
        "Sein"
)