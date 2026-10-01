package it.futurecraft.sorrentino.event

import kotlinx.atomicfu.atomic
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.reflect.KClass

internal class EventBusImpl : EventBus {
    private val _listeners = ConcurrentHashMap<KClass<out Event>, CopyOnWriteArrayList<Listener<*>>>()

    private val _orderCounter = atomic(0)

    override fun <T : Event> publish(event: T): Boolean {
        val listeners = _listeners[event::class] ?: return false

        for (listener in listeners) {
            if (event is Event.Cancellable && event.cancelled && !listener.ignoreCancelled) {
                continue
            }

            val subscriber = listener.subscriber as EventSubscriber<T>
            subscriber.on(event)
        }

        return event is Event.Cancellable && !event.cancelled
    }

    override fun <T : Event> subscribe(
        type: KClass<T>,
        priority: EventSubscriber.Priority,
        ignoreCancelled: Boolean,
        handler: EventSubscriber<T>
    ): EventSubscription<T> {
        val listener = Listener(type, priority, ignoreCancelled, handler, _orderCounter.incrementAndGet())
        val listeners = _listeners.computeIfAbsent(type) { CopyOnWriteArrayList() }

        listeners.add(listener)
        listeners.sortWith(compareBy(Listener<*>::priority, Listener<*>::order))

        return Subscription(type, listener)
    }

    private inner class Subscription<T : Event>(override val event: KClass<out T>, private val _listener: Listener<T>) : EventSubscription<T> {
        private val _registered = atomic(true)

        override val registered: Boolean
            get() = _registered.value

        override fun dispose() {
            if (!_registered.compareAndSet(true, false)) {
                return
            }

            val listeners = _listeners[event] ?: return
            listeners.remove(_listener)

            if (listeners.isEmpty()) {
                _listeners.remove(event, listeners)
            }
        }
    }
}