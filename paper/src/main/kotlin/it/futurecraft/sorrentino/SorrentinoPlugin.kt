package it.futurecraft.sorrentino

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.philippheuer.events4j.core.EventManager
import com.github.philippheuer.events4j.simple.SimpleEventHandler
import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.common.util.EventManagerUtils
import it.futurecraft.sorrentino.http.HttpServer
import kotlinx.coroutines.*
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module

class SorrentinoPlugin : JavaPlugin(), KoinComponent, Listener {
    private val _module = module {
        single<Sorrentino> { SorrentinoImpl() }
        single<SorrentinoPlugin> { this@SorrentinoPlugin }
    }

    private val _scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _http: HttpServer = HttpServer()

    private val _api by inject<Sorrentino>()

    val eventManager: EventManager = EventManagerUtils.initializeEventManager(
        SimpleEventHandler::class.java
    )

    override fun onEnable() {
        startKoin { modules(_module) }

        _http.start()
    }

    override fun onDisable() {
        _http.close()

        _scope.cancel()
    }


    fun launch(block: suspend CoroutineScope.() -> Unit): Job = _scope.launch(block = block)
}
