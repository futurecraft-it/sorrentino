package it.futurecraft.sorrentino.auth.flow;

import com.github.twitch4j.auth.domain.TwitchScopes;
import com.google.inject.Inject;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import it.futurecraft.sorrentino.Sorrentino;
import it.futurecraft.sorrentino.auth.ClientIdentity;
import it.futurecraft.sorrentino.auth.Device;
import it.futurecraft.sorrentino.services.AuthenticationService;
import it.futurecraft.sorrentino.utils.wrappers.PlayerWrapper;
import it.futurecraft.sorrentino.utils.wrappers.SchedulerWrapper;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class DeviceFlowController implements FlowController {
    private final Sorrentino api;
    private final ClientIdentity identity;
    private final SchedulerWrapper scheduler;

    private final Moshi moshi = new Moshi.Builder().build();
    protected final OkHttpClient client = new OkHttpClient();

    @Inject
    public DeviceFlowController(Sorrentino api, ClientIdentity credentials, SchedulerWrapper scheduler) {
        this.api = api;
        this.identity = credentials;
        this.scheduler = scheduler;
    }

    @Override
    public CompletableFuture<Void> start(@NotNull PlayerWrapper target, @NotNull List<TwitchScopes> scopes) {
        return device(scopes).thenAccept(device -> {


            scheduler.run(() -> {
                Instant expiration = Instant.now().plusSeconds(device.expiresIn());

                scheduler.async(() -> {
                    poll(target, scopes, device, expiration).thenAccept(success -> {

                    });
                });
            });
        });
    }

    protected CompletableFuture<Device> device(@NotNull List<TwitchScopes> scopes) {
        CompletableFuture<Device> future = new CompletableFuture<>();

        RequestBody body = new FormBody.Builder()
                .add("client_id", identity.id())
                .add("scope", scopes.stream().map(TwitchScopes::toString).collect(Collectors.joining(" ")))
                .build();

        Request req = new Request.Builder()
                .url(AuthenticationService.ENDPOINT + "/device")
                .post(body)
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (ResponseBody body = response.body()) {
                    if (!response.isSuccessful()) {
                        future.completeExceptionally(new IOException("Unexpected code " + response));
                    } else {
                        JsonAdapter<Device> adapter = moshi.adapter(Device.class);
                        Device device = adapter.fromJson(body.source());

                        future.complete(device);
                    }
                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
            }
        });

        return future;
    }

    protected CompletableFuture<Boolean> poll(PlayerWrapper target, List<TwitchScopes> scopes, Device device, Instant expiration) {
        if (Instant.now().isAfter(expiration)) {
            // TODO: IMPLEMENT TIME OUT

            return CompletableFuture.completedFuture(false);
        }

        CompletableFuture<Boolean> future = new CompletableFuture<>();
        RequestBody body = new FormBody.Builder()
                .add("client_id", identity.id())
                .add("device_code", device.deviceCode())
                .add("scopes", scopes.stream().map(TwitchScopes::toString).collect(Collectors.joining(" ")))
                .add("grant_type", "urn:ietf:params:oauth:grant-type:device_code")
                .build();

        Request req = new Request.Builder()
                .url(AuthenticationService.ENDPOINT + "/token")
                .post(body)
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (ResponseBody body = response.body()) {
                    if (response.code() == 200) {
                        JsonAdapter<Credentials> adapter = moshi.adapter(Credentials.class);
                        Credentials credentials = adapter.fromJson(body.source());

                        // TODO: Add credentials store

                        future.complete(true);
                    }
                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
            }
        });

        return future;
    }

    private static class Credentials {
        @Json(name = "access_token")
        public String access;

        @Json(name = "refresh_token")
        public String refresh;

        @Json(name = "expires_in")
        public int expiresIn;
    }
}
