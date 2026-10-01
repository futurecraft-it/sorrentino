package it.futurecraft.sorrentino.utils.wrapper

import kotlinx.coroutines.CoroutineScope

interface SchedulerWrapper : Wrapper {
    fun async(block: suspend CoroutineScope.() -> Unit)

    fun sync(block: () -> Unit)
}