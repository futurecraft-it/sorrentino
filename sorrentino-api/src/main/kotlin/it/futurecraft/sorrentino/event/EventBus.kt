package it.futurecraft.sorrentino.event

import kotlin.reflect.KClass

/**
 * The plugin's main event bus.
 * Use this to subscribe to sorrentino's events.
 */
interface EventBus {
    /**
     * Publish an event.
     *
     * @param event The event to publish.
     * @return true if the event was published successfully, false otherwise.
     */
    fun <T : Event> publish(event: T): Boolean

    /**
     * Subscribe to an event.
     *
     * @param T The event type.
     * @param priority The subscriber priority
     * @param ignoreCancelled Whether to ignore cancelled events
     * @param handler The event handler.
     */
    fun <T : Event> subscribe(
        type: KClass<T>,
        priority: EventSubscriber.Priority = EventSubscriber.Priority.NORMAL,
        ignoreCancelled: Boolean = false,
        handler: EventSubscriber<T>
    ): EventSubscription<T>
}

/**
 * Subscribe to an event.
 *
 * @param T The event type.
 * @param priority The subscriber priority
 * @param ignoreCancelled Whether to ignore cancelled events
 * @param handler The event handler.
 */
inline fun <reified T : Event> EventBus.subscribe(
    priority: EventSubscriber.Priority = EventSubscriber.Priority.NORMAL,
    ignoreCancelled: Boolean = false,
    noinline handler: (T) -> Unit
): EventSubscription<T> {
    return subscribe(T::class, priority, ignoreCancelled, handler)
}
