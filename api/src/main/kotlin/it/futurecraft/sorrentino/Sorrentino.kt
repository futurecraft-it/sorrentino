package it.futurecraft.sorrentino

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface Sorrentino {
    /**
     * The api version of Sorrentino.
     */
    val version: Version
}