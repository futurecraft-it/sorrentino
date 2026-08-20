package it.futurecraft.sorrentino.database.enitities;

import it.futurecraft.sorrentino.database.enities.UserEntity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UserEntityImpl implements UserEntity {
    private final UUID uuid;
    private String displayName;
    private String twitchUsername;
    private int twitchId;
    private boolean streamer;

    public UserEntityImpl(UUID uuid) {
        this.uuid = uuid;
    }

    public UserEntityImpl(UUID uuid, String displayName, String twitchUsername, int twitchId, boolean streamer) {
        this.uuid = uuid;
        this.displayName = displayName;
        this.twitchUsername = twitchUsername;
        this.twitchId = twitchId;
        this.streamer = streamer;
    }

    @Override
    public @NotNull String displayName() {
        return displayName;
    }

    @Override
    public void displayName(@NotNull String value) {
        this.displayName = value;
    }

    @Override
    public int twitchId() {
        return twitchId;
    }

    @Override
    public void twitchId(int value) {
        this.twitchId = value;
    }

    @Override
    public @NotNull String twitchUsername() {
        return twitchUsername;
    }

    @Override
    public void twitchUsername(@NotNull String value) {
        this.twitchUsername = value;
    }

    @Override
    public boolean streamer() {
        return streamer;
    }

    @Override
    public void streamer(boolean value) {
        this.streamer = value;
    }

    @Override
    public UUID id() {
        return uuid;
    }
}
