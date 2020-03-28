package eastvillage.cj20s.game

interface Targetable {
    val longname: String
    val shortName: String
    val health: Health
    val isDead: Boolean
}