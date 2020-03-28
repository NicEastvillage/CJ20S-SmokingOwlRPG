package eastvillage.cj20s

import eastvillage.cj20s.game.Player
import net.dv8tion.jda.core.entities.User

object PlayerManager {

    private val players = mutableMapOf<String, Player>()
    val allPlayers: List<Player> get() = ArrayList(players.values)

    fun get(id: String): Player? = players[id]

    fun registerIfNew(user: User) {
        if (players[user.id] == null) {
            players[user.id] = Player(user)
        }
    }

    fun getOrRegister(user: User): Player {
        registerIfNew(user)
        return get(user.id)!!
    }
}