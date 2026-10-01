package it.futurecraft.sorrentino.event

import kotlin.reflect.KClass

/**
 * A subscription to a given event.
 * @param T The event type to subscribe to.
 */
interface EventSubscription<T : Event> {
    /**
     * The event type this subscription is for.
     */
    val event: KClass<out T>

    /**
     * Whether the subscription is still registered.
     */
    val registered: Boolean

    /**
     * Unregisters the subscription.
     */
    fun dispose()
}