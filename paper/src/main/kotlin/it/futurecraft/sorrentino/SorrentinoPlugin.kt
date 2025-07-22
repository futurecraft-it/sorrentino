package it.futurecraft.sorrentino

import it.futurecraft.sorrentino.http.HttpServer
import org.bukkit.plugin.java.JavaPlugin

class SorrentinoPlugin : JavaPlugin() {
    val sorrentino: Sorrentino = SorrentinoImpl(this)

    val http: HttpServer = HttpServer()

    override fun onEnable() {
        http.start()
    }

    override fun onDisable() {
        http.close()
    }
}