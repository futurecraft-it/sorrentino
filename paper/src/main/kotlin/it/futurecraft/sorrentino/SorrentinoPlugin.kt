package it.futurecraft.sorrentino

import com.github.philippheuer.events4j.simple.SimpleEventHandler
import com.github.twitch4j.common.util.EventManagerUtils
import it.futurecraft.sorrentino.http.HttpServer
import kotlinx.coroutines.*
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.dsl.module

class SorrentinoPlugin : JavaPlugin(), KoinComponent {
    private val _module = module {
        single<Sorrentino> { SorrentinoImpl() }
        single<SorrentinoPlugin> { this@SorrentinoPlugin }
    }

    private val _job = SupervisorJob()
    private val _scope = CoroutineScope(Dispatchers.IO + _job)

    private val _http: HttpServer = HttpServer()

    val eventManager = EventManagerUtils.initializeEventManager(SimpleEventHandler::class.java)

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
