package it.futurecraft.sorrentino

import org.bukkit.plugin.java.JavaPlugin

class SorrentinoPlugin : JavaPlugin() {

    override fun onEnable() {
        logger.info("${this.name} enabled")
    }

}