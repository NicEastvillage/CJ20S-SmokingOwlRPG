package eastvillage.cj20s.game

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
}