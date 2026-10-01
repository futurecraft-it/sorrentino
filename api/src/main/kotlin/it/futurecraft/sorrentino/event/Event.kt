package it.futurecraft.sorrentino.event

interface Event {
    interface Cancellable : Event {
        var cancelled: Boolean
    }
}