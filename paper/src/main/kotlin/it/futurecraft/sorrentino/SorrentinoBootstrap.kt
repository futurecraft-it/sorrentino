@file:Suppress("UnstableApiUsage")

package it.futurecraft.sorrentino

import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.bootstrap.PluginProviderContext
import org.bukkit.plugin.java.JavaPlugin

class SorrentinoBootstrap : PluginBootstrap {
    override fun bootstrap(ctx: BootstrapContext) {
    }

    override fun createPlugin(ctx: PluginProviderContext): JavaPlugin {
        return SorrentinoPlugin()
    }
}