package it.futurecraft.sorrentino.utils.wrapper

import it.futurecraft.sorrentino.event.Event
import javax.xml.crypto.Data

/**
 * Wraps the
 */
interface DispatcherWrapper : Wrapper {
    fun <T : Event> dispatch(event: T): Boolean
}