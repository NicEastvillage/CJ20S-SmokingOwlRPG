package eastvillage.cj20s.game

import kotlin.random.Random

class Monster(
        val realname: String?,
        val race: String,
        val attacks: List<MonsterAttack>,
        override val health: Health,
        val isBoss: Boolean
) : Targetable {
    override val longname: String get() = if (realname == null) "the $race" else "$realname the $race"
    override val shortName: String get() = "the $race"
    override val isDead: Boolean get() = health.isDead

    companion object {
        fun randomMonster(): Monster {
            val race = monsterRaces.random()
            return Monster(null, race.race, race.attacks, Health(Random.nextInt(race.healthMin, race.healthMax)), false)
        }
        fun randomBoss(): Monster {
            val race = bossRaces.random()
            return Monster(bossNames.random(), race.race, race.attacks, Health(Random.nextInt(race.healthMin, race.healthMax)), true)
        }
    }
}


class MonsterRace(val race: String, val attacks: List<MonsterAttack>, val healthMin: Int, val healthMax: Int, val isBoss: Boolean)


val monsterRaces = listOf(
        MonsterRace("goblin", listOf(Bite, Slash), 10, 14, false),
        MonsterRace("giant rat", listOf(Bite), 10, 14, false),
        MonsterRace("imp", listOf(Claw, Swipe), 12, 15, false),
        MonsterRace("wolf", listOf(Claw, Swipe, Pounce), 11, 16, false),
        MonsterRace("bear", listOf(Claw, Swipe), 13, 17, false),
        MonsterRace("skeleton", listOf(Slash), 10, 16, false),
        MonsterRace("zombie", listOf(Bite), 14, 15, false),
        MonsterRace("demon", listOf(Regenerate, Claw), 16, 19, false),
//        MonsterRace("elemental", listOf()),
//        MonsterRace("dragon welp", listOf()),
//        MonsterRace("siren", listOf()),
        MonsterRace("panther", listOf(Claw, Swipe, Pounce), 10, 15, false),
        MonsterRace("ogre", listOf(Smash), 15, 17, false),
        MonsterRace("orc", listOf(Smash), 15, 17, false),
        MonsterRace("kobold", listOf(Slash), 11, 12, false),
//        MonsterRace("satyr", listOf(Regenerate)),
        MonsterRace("troll", listOf(Smash, Regenerate), 13, 16, false)
)

val bossRaces = listOf(
        MonsterRace("dragon", listOf(Claw), 30, 40, true),
        MonsterRace("manticore", listOf(Claw, Swipe, Pounce), 25, 37, true),
        MonsterRace("lich", listOf(Slash, Regenerate), 31, 37, true),
        MonsterRace("necromancer", listOf(Regenerate, Slash), 27, 34, true),
        MonsterRace("golem", listOf(Smash, Swipe), 28, 34, true),
        MonsterRace("hydra", listOf(Bite, Swipe), 33, 40, true),
        MonsterRace("basilisk", listOf(Bite, Pounce), 30, 37, true),
        MonsterRace("gelatinous cube", listOf(Regenerate, Bite), 20, 40, true),
        MonsterRace("minotaur", listOf(Slash, Smash), 33, 34, true)
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