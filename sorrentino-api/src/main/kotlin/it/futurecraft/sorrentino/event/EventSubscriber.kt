package it.futurecraft.sorrentino.event

/**
 * A subscriber to sorrentino's events.
 */
fun interface EventSubscriber<T : Event> {
    fun on(event: T)

    enum class Priority { LOWEST, LOW, NORMAL, HIGHEST, HIGH, MONITOR }
}