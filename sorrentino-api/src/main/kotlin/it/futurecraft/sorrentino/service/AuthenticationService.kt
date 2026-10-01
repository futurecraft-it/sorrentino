package it.futurecraft.sorrentino.service

interface AuthenticationService {
    companion object {
        @JvmStatic
        val ENDPOINT = "https://id.twitch.tv/oauth2"
    }
}