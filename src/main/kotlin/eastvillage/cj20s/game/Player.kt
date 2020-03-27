package eastvillage.cj20s.game

import net.dv8tion.jda.core.entities.User

data class Player(
        val id: String,
        val name: String,
        val discriminator: String
) {
    constructor(user: User) : this(user.id, user.name, user.discriminator)

    var character: Character? = null

    override fun toString(): String {
        return "$name#$discriminator"
    }

    fun asMention(): String {
        return "@${toString()}"
    }
}