plugins {
    id("sorrentino.common-conventions")
}

dependencies {
    api(libs.kotlin.stdlib)
    api(libs.kotlinx.datetime)
    api(libs.kotlinx.coroutines)
    api(libs.kotlinx.serialization.core)

    compileOnlyApi(libs.bundles.twitch4j) {
        exclude(group = "com.github.twitch4j", module = "twitch4j-eventsub-websocket")

        exclude(group = "com.github.twitch4j", module = "twitch4j-chat")
        exclude(group = "com.github.twitch4j", module = "twitch4j-pubsub")
        exclude(group = "com.github.twitch4j", module = "twitch4j-graphql")
        exclude(group = "com.github.twitch4j", module = "twitch4j-tmi")
        exclude(group = "com.github.twitch4j", module = "twitch4j-kraken")

        exclude(group = "com.github.philippheuer.events4j", module = "events4j-handler-reactor")
        exclude(group = "com.github.philippheuer.events4j", module = "events4j-handler-spring")
    }
}
