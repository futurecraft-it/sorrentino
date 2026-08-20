package it.futurecraft.sorrentino.http;

import com.google.inject.Inject;
import io.javalin.Javalin;
import it.futurecraft.sorrentino.Sorrentino;
import it.futurecraft.sorrentino.configuration.structures.Web;
import it.futurecraft.sorrentino.http.controllers.WebhookController;
import it.futurecraft.sorrentino.http.plugins.VerifySignaturePlugin;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;

public class WebServer implements Closeable {
    private final Sorrentino api;

    private final Javalin app;

    @Inject
    public WebServer(@NotNull Sorrentino api) {
        this.api = api;

        this.app = Javalin.create(config -> {
            config.registerPlugin(new VerifySignaturePlugin(cfg -> {
                cfg.secret = api.configuration().credentials().secret();
                cfg.ignorList = Collections.emptyList();
            }));

            config.routes.post("/webhook/callback", WebhookController::callback);
        });
    }

    public void start() {
        Web conf = api.configuration().web();
        app.start(conf.hostname(), conf.port());
    }

    @Override
    public void close() {
        app.stop();
    }
}
