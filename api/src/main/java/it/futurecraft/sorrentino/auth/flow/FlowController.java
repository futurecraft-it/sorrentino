package it.futurecraft.sorrentino.auth.flow;

import com.github.twitch4j.auth.domain.TwitchScopes;
import it.futurecraft.sorrentino.utils.wrappers.PlayerWrapper;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface FlowController {
    CompletableFuture<Void> start(@NotNull PlayerWrapper target, @NotNull List<TwitchScopes> scopes);
}
