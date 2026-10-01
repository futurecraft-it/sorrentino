package it.futurecraft.sorrentino.event

import kotlin.reflect.KClass

internal data class Listener<T : Event> (
    val type: KClass<T>,

    val priority: EventSubscriber.Priority,

    val ignoreCancelled: Boolean,

    val subscriber: EventSubscriber<T>,

    val order: Int
)