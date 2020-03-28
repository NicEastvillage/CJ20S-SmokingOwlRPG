package eastvillage.cj20s.game

import kotlin.random.Random

class Monster(
        val realname: String?,
        val race: String,
        override val health: Health,
        val isBoss: Boolean
) : Targetable {
    override val longname: String get() = if (realname == null) "the $race" else "$realname the $race"
    override val shortName: String get() = "the $race"
    override val isDead: Boolean get() = health.isDead

    companion object {
        fun randomMonster(): Monster = Monster(null, monsterRaces.random(), Health(Random.nextInt(20, 26)), false)
        fun randomBoss(): Monster = Monster(bossNames.random(), bossRaces.random(), Health(Random.nextInt(40, 50)), true)
    }
}


val monsterRaces = listOf(
        "goblin",
        "giant rat",
        "imp",
        "wolf",
        "bear",
        "skeleton",
        "zombie",
        "demon",
        "elemental",
        "dragon welp",
        "siren",
        "panther",
        "ogre",
        "grimlin",
        "orc",
        "kobold",
        "satyr",
        "troll"
)

val bossRaces = listOf(
        "dragon",
        "manticore",
        "lich",
        "necromancer",
        "golem",
        "hydra",
        "basilisk",
        "gelatinous cube",
        "minotaur"
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