package eastvillage.cj20s.game

import eastvillage.cj20s.Emoji
import java.lang.StringBuilder

class Health(
        private var maxHealth: Int
) {
    var currentHealth: Int = maxHealth; private set

    val isDead: Boolean get() = currentHealth <= 0
    val asPercentage: Float get() = currentHealth.toFloat() / maxHealth

    fun takeDamage(amount: Int) {
        currentHealth -= amount
    }

    fun heal(amount: Int) {
        currentHealth = minOf(currentHealth + amount, maxHealth)
    }

    fun setMaxHealth(amount: Int) {
        maxHealth = amount
        heal(amount)
    }

    fun getMaxHealth(): Int = maxHealth

    fun asEmojis(withText: Boolean = true, length: Int = 8): String {
        val halves = length * 2
        var greenHalves = (asPercentage * length * 2).toInt()
        var redHalves = halves - greenHalves
        val sb = StringBuilder()
        if (withText) {
            sb.append("($currentHealth/$maxHealth hp)")
        }
        sb.append(Emoji.HEALTH_BAR_END_LEFT)
        while (greenHalves >= 2) {
            greenHalves -= 2
            sb.append(Emoji.HEALTH_BAR_FULL)
        }
        if (greenHalves == 1) {
            sb.append(Emoji.HEALTH_BAR_HALF)
        }
        while (redHalves >= 2) {
            redHalves -= 2
            sb.append(Emoji.HEALTH_BAR_EMPTY)
        }
        sb.append(Emoji.HEALTH_BAR_END_RIGHT)
        return sb.toString()
    }
}