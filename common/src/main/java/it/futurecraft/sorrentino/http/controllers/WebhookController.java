package it.futurecraft.sorrentino.http.controllers;

import com.github.twitch4j.common.util.TypeConvert;
import com.github.twitch4j.eventsub.EventSubNotification;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

public class WebhookController {
    public static void callback(@NotNull Context ctx) {
        String type = ctx.header("Twitch-Eventsub-Message-Type");

        if (type == null) {
            throw new BadRequestResponse("Missing message type");
        }

        switch (type) {
            case "revocation":
                revocation(ctx);
                break;
            case "notification":
                notification(ctx);
                break;
            case "webhook_callback_verification":
                callback_verification(ctx);
                break;
            default:
                throw new BadRequestResponse("Invalid message type");
        }
    }

    private static void callback_verification(@NotNull Context ctx) {
        String body = ctx.body();

        EventSubNotification event = TypeConvert.jsonToObject(body, EventSubNotification.class);
        String challenge = event.getChallenge();

        if (challenge == null) {
            throw new ForbiddenResponse("Missing challenge");
        }

        ctx.status(HttpStatus.OK).result(challenge);
    }

    private static void notification(@NotNull Context ctx) {
        String body = ctx.body();

        EventSubNotification event = TypeConvert.jsonToObject(body, EventSubNotification.class);
        // TODO: implementare

        ctx.status(HttpStatus.NO_CONTENT);
    }

    private static void revocation(@NotNull Context ctx) {
        String body = ctx.body();

        EventSubNotification event = TypeConvert.jsonToObject(body, EventSubNotification.class);
        // TODO: implementare qualcosa per la revocazione

        ctx.status(HttpStatus.NO_CONTENT);
    }
}
