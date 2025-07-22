package it.futurecraft.sorrentino

class SorrentinoImpl(private val plugin: SorrentinoPlugin) : Sorrentino {
    override val version: Version = Version.V1_0
}