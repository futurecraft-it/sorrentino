package it.futurecraft.sorrentino.event

interface Event {
    interface Cancellable : Event {
        /**
         * Whether the event has been cancelled or not.
         */
        var cancelled: Boolean
    }
}