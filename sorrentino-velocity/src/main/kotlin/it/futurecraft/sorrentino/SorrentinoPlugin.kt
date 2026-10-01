package it.futurecraft.sorrentino

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import xyz.jpenilla.gremlin.runtime.platformsupport.VelocityClasspathAppender
import java.nio.file.Path

@Plugin(id = "it.futurecraft.sorrentino")
class SorrentinoPlugin @Inject constructor (@DataDirectory val dataDirectory: Path, val logger: Logger, val proxy: ProxyServer) {

    @Subscribe
    fun onProxyInitialize(event: ProxyInitializeEvent) {
        val out = dataDirectory.resolve("libs")
        VelocityClasspathAppender(proxy, this).append(out)
    }
}