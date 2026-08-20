package it.futurecraft.sorrentino.http.plugins;

import io.javalin.config.JavalinState;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.ForbiddenResponse;
import io.javalin.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.function.Consumer;

public class VerifySignaturePlugin extends Plugin<VerifySignaturePlugin.Config> {
    public static class Config {
        public @NotNull String secret;
        public @NotNull List<String> ignorList;
    }

    public VerifySignaturePlugin(@NotNull Consumer<Config> userConfig) {
        super(userConfig, null);
    }

    @Override
    public void onInitialize(@NotNull JavalinState state) {
        state.routes.before(ctx -> {
            if (pluginConfig.ignorList.contains(ctx.path())) return;

            String signature = ctx.header("Twitch-Eventsub-Message-Signature");
            String messageId = ctx.header("Twitch-Eventsub-Message-Id");
            String timestamp = ctx.header("Twitch-Eventsub-Message-Timestamp");

            if (signature == null || messageId == null || timestamp == null) {
                throw new BadRequestResponse("Missing required headers.");
            }

            String body = ctx.body();
            String message = messageId + timestamp + body;

            String expected = hmac(message);

            if (!expected.equals(signature)) {
                throw new ForbiddenResponse("Invalid signature.");
            }
        });
    }

    private String hmac(@NotNull String message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec key = new SecretKeySpec(pluginConfig.secret.getBytes(), "HmacSHA256");

        mac.init(key);
        byte[] result = mac.doFinal(message.getBytes());

        return HexFormat.of().formatHex(result);
    }
}
