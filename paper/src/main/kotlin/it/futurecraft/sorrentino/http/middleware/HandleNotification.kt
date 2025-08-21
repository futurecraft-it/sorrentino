package it.futurecraft.sorrentino.http.middleware

import com.github.twitch4j.common.util.TypeConvert
import com.github.twitch4j.eventsub.EventSubNotification
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import it.futurecraft.sorrentino.SorrentinoPlugin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

suspend fun handleNotification(call: ApplicationCall) {
    val notification: EventSubNotification = TypeConvert.jsonToObject(call.receiveText(), EventSubNotification::class.java)

    val instance = object : KoinComponent {
        val plugin: SorrentinoPlugin by inject()
    }

    instance.plugin.eventManager.publish(notification)

    call.response.status(HttpStatusCode.NoContent)
}
