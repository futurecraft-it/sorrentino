package it.futurecraft.sorrentino.utils.wrapper

interface Wrapper {
    fun <T> unwrap(type: Class<T>): T
}