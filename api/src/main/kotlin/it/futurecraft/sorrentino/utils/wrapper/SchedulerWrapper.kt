package it.futurecraft.sorrentino.utils.wrapper

interface SchedulerWrapper : Wrapper {
    fun async(block: () -> Unit)

    fun sync(block: () -> Unit)
}