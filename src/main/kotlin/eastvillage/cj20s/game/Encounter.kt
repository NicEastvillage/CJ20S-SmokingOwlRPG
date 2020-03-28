package eastvillage.cj20s.game

class Encounter(
        val monster: Monster
) {
    val frontline: Frontline = Frontline()
    val queuedAttack: String = "TODO"
}