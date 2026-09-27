package it.futurecraft.sorrentino

import kotlinx.serialization.Serializable

@Serializable
data class CatalogueItem(val groupId: String, val artifactId: String, val version: String) {
    override fun toString() = "$groupId:$artifactId:$version"
}

typealias Catalogue = Map<String, CatalogueItem>
