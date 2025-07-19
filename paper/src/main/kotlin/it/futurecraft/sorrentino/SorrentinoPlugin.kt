package it.futurecraft.sorrentino

import it.futurecraft.sorrentino.http.HttpServer
import it.futurecraft.sorrentino.http.HttpServerConfiguration
import net.kyori.adventure.text.logger.slf4j.ComponentLogger
import org.bukkit.plugin.java.JavaPlugin

class SorrentinoPlugin : JavaPlugin() {
    val sorrentino: Sorrentino = SorrentinoImpl()

    val logger: ComponentLogger = ComponentLogger.logger("sorrentino")

    private val http: HttpServer = HttpServer(HttpServerConfiguration())

    override fun onEnable(){
        http.start()
    }

    override fun onDisable()  {
        http.stop()
    }
}