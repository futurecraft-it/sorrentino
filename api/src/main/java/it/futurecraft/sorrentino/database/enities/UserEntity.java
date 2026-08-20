package it.futurecraft.sorrentino.database.enities;

import it.futurecraft.sorrentino.database.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface UserEntity extends Entity<UUID> {
    @NotNull String displayName();

    void displayName(@NotNull String value);

    int twitchId();

    void twitchId(int value);

    @NotNull String twitchUsername();

    void twitchUsername(@NotNull String value);

    boolean streamer();

    void streamer(boolean value);
}
