package it.futurecraft.sorrentino.events

import it.futurecraft.sorrentino.NotificationEvent
import it.futurecraft.sorrentino.SorrentinoPlugin
import it.futurecraft.sorrentino.twitch.events.stream.StreamOfflineEvent
import it.futurecraft.sorrentino.twitch.events.stream.StreamOnlineEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class NotificationEventListener(val sorrentino: SorrentinoPlugin) : Listener {
    @EventHandler
    fun onTwitchEvent(event: NotificationEvent) {
        if (event.twitchEvent is StreamOnlineEvent) {
            // TODO: Implement notification for stream online
            // TODO: Implement live prefix
        }

        if (event.twitchEvent is StreamOfflineEvent) {
            // TODO: Implement offline prefix
        }
    }
}