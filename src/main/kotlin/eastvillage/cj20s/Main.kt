/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package eastvillage.cj20s

import eastvillage.cj20s.game.*
import eastvillage.cj20s.game.dungeon.Direction
import eastvillage.cj20s.game.dungeon.MoveOutcome
import eastvillage.cj20s.game.dungeon.dungeons
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Needs one argument ('bot-token')")
    } else {
        val jda = JDABuilder(args[0]).build()
        jda.addEventListener(MessageListener())

        println(dungeons)
    }
}

class MessageListener : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (!event.author.isBot) {
            println("Msg received from '${event.author.name}' (${event.author.id}): ${event.message.contentRaw}")
            PlayerManager.registerIfNew(event.author)
            val player = PlayerManager.getOrRegister(event.author)
            if (event.message.contentRaw.startsWith("!")) {
                val response = parseCommand(player, event.message.contentRaw)
                when (response) {
                    is TextResponse -> event.channel.sendMessage(response.msg).queue()
                    is DungeonResponse -> event.channel.sendMessage("${response.msg}\n${DungeonManager.dungeon.asEmotes()}").queue()
                    is ErrorResponse -> event.channel.sendMessage("${listOf(
                            "I don't understand.. ?",
                            "That's gibberish!",
                            "Ehm, so I am not the only one here smoking, I see.. ?",
                            "Sorry, what did you mean to say?",
                            "That's not how it works!",
                            "No no no...",
                            "Uhhhhhhhhh"
                    ).random()}\n${response.msg}").queue()
                }
            }
        }
    }
}

fun parseCommand(player: Player, command: String): Response {
    val pieces = command.split(' ')
    val func = pieces[0]
    val args = if (pieces.size > 1) pieces.subList(1, pieces.size) else listOf()
    println(pieces)
    return when (func) {
        "!hello" -> expect(0, args, "!hello") ?: TextResponse("Hello my friend!")
        "!help" -> expect(0, args, "!help") ?: helpResponse()
        "!createcharacter" -> expect(2, args, "!createcharacter <name> (wizard|sorcerer|priest|warlock)")
                ?: createCharacter(player, args[0], args[1])
        "!suicide" -> expect(0, args, "!suicide") ?: suicide(player)
        "!dungeon" -> expect(0, args, "!dungoen")
                ?: DungeonResponse("You are right there. \\*points awkwardly with a claw\\*")
        "!move" -> expect(1, args, "!move (north|south|west|east)") ?: resolveMove(player, args[0])
        "!cast" -> tryCast(player, args)
        else -> NoResponse
    }
}

fun expect(expected: Int, args: List<String>, format: String): ErrorResponse? {
    if (args.size != expected) {
        return ErrorResponse("Wrong number of arguments. The format of the command is:\n$format")
    }
    return null
}

fun helpResponse(): TextResponse {
    return TextResponse("""
        Hello. I am your dungeon master. I will be guiding you and your party through the dungeon!
        Use the following commands to let me know what you want to do:
        !createcharacter <name> (wizard|sorcerer|priest|warlock)
        !suicide
        
        The "<something>" means you must enter something valid. (a|b|c) means you must choose one of a, b, or c. And [a|b|c] means you can optionally choose a, b, or c.
    """.trimIndent())
}

fun createCharacter(player: Player, characterName: String, classStr: String): TextResponse {
    if (player.character == null) {
        val pcclass = when (classStr) {
            "wizard" -> PCClass.WIZARD
            "sorcerer" -> PCClass.SORCERER
            "priest" -> PCClass.PRIEST
            "warlock" -> PCClass.WARLOCK
            else -> return TextResponse("Sorry, in this world you can only be a wizard, sorcerer, priest, or warlock. Not '$classStr'. Did you not read the rules!??!")
        }
        player.character = Character(player, characterName, pcclass)
        return TextResponse("Very well. You now have a $pcclass named $characterName. ${listOf(
                "Let the adventure begin!",
                "Can't wait to tell their story!",
                "I already like them!",
                "May they become a great adventurer!",
                "I am sure they are nice!",
                "Interesting character!"
        ).random()}")
    } else {
        val character = player.character!!
        return TextResponse("""
            Wait a second. You already have a character, ${character.pcname} the ${character.pcClass}.
            If you want to get rid of him, use !suicide. But keep it clean, okay?
        """.trimIndent())
    }
}

fun suicide(player: Player): TextResponse {
    val character = player.character
    if (character == null) {
        return TextResponse("${listOf(
                "You don't even have a character.",
                "Ha ha, nice try.",
                "Wow. Well..."
        ).random()} First you need to create one with the !createcharacter command.")
    } else {
        player.character = null
        return TextResponse("${listOf(
                "Okay. There's no going back though!",
                "Brutal.",
                "Oof, this is gonna be ugly."
        ).random()}\n${listOf(
                "*The party glance back and finds ${character.pcname} lying on the floor behind them. Dead. Shocked they looked around the room, but they cannot see the cause of the ${character.pcClass}'s demise*",
                "*A sudden fear grasps ${character.pcname}, and they run out of the room screaming. The party does not have time to react before the ${character.pcClass} is gone forever*",
                "*${character.pcname} spontaneously combust. May them rest in pieces*"
        ).random()}\n\n\\*Sigh\\* Create a new character with the !createcharacter command.")
    }
}

fun resolveMove(player: Player, dirStr: String): Response {
    val dir = when (dirStr) {
        "north" -> Direction.NORTH
        "south" -> Direction.SOUTH
        "west" -> Direction.WEST
        "east" -> Direction.EAST
        else -> return ErrorResponse("You can only move north, south, west, or east!")
    }

    val outcome = DungeonManager.dungeon.move(dir)
    return when (outcome) {
        MoveOutcome.SUCCESS -> DungeonResponse("You move $dir")
        MoveOutcome.FINDS_KEY -> DungeonResponse(listOf(
                "Despite your incompetence, you manage find a key!",
                "You move $dir. As you are about to move on something catches your eye. A golden key is lying the in the dust",
                "An pile of bones lies before you. Creepily you investigate the poor guy's remains - and find a key!",
                "A key lies on the ground. You decide to pick it up as it might be useful"
        ).random())
        MoveOutcome.WALL -> DungeonResponse(listOf(
                "Eh, you can't",
                "You walk $dir and bang your head into the wall ..",
                "A wall blocks your way",
                "There seem to be a wall there"
        ).random())
        MoveOutcome.NEED_KEY -> DungeonResponse(listOf(
                "There's a door and it's locked",
                "You try the door handle, but it's locked",
                "You realize that you don't have the key and facepalms",
                "The door to the $dir does not move"
        ).random())
        MoveOutcome.OPEN_DOOR -> DungeonResponse(listOf(
                "You slide the key into the keyhole ... it fits! The door opens",
                "Carefully you unlock the door and push it open. You stare into another dark hallway",
                "The door creaks as you open it"
        ).random())
    }
}

fun tryCast(player: Player, args: List<String>): Response {
    if (args.isEmpty() || args.size > 2) return ErrorResponse("""
    Wrong number of arguments. The format of the command is
    !cast <spell> [<character>|enemy]
    """.trimIndent())

    val character = player.character
    if (character == null) return TextResponse("${listOf(
            "Silly human.",
            "Your spell fizzles .. because there is no one to cast it.",
            "Spells don't appear from thin air ..."
    ).random()}\nYou can't cast spells without a character. Create a character with the !createcharacter command")

    if (character.isDead) return TextResponse("${listOf(
            "The ghost of ${character.pcname} the ${character.pcClass} tries to cast a spell - but ghosts cannot cast spells.",
            "Your character is dead. Sorry not sorry.",
            "You can't cast spells when your character is dead.",
            "Well, too late my friend. Your character ${character.pcname} is dead."
    ).random()} Create a new character with the !createcharacter command")

    val spell = character.spells.firstOrNull { it.name == args[0] }
            ?: return ErrorResponse("You don't have a spell named ${args[0]}.")

    val encounter = EncounterManager.encounter ?: return ErrorResponse("You can only cast spells while in combat.")

    val outcome = when (spell) {
        is TargetedAttack -> {
            if (args.size == 2) {
                val targetName = args[1]
                if (targetName == "enemy") {
                    spell.perform(character, encounter.monster.health)
                } else {
                    val allCharacters = PlayerManager.allPlayers.map { it.character }
                    val target = allCharacters.firstOrNull { it != null && it.pcname == targetName }
                    if (target == null) return ErrorResponse("$targetName is not a valid target. Write the name of either a character or simply 'enemy'")
                    // TODO Target might be dead??
                    spell.perform(character, target.health)
                }
            } else {
                return ErrorResponse("""
                Wrong number of arguments. The ${spell.name} spell requires a target. Write the name of the target, either a character name or 'enemy':
                !cast <spell> [<character>|enemy]
                """.trimIndent())
            }
        }
        is UntargetedAttack -> {
            if (args.size == 1) {
                spell.perform(character)
            } else {
                return ErrorResponse("Wrong number of arguments. The ${spell.name} spell does not require a target.")
            }
        }
    }
}