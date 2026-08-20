package it.futurecraft.sorrentino.database.enitities;

import it.futurecraft.sorrentino.database.enities.CredentialsEntity;
import it.futurecraft.sorrentino.database.enities.UserEntity;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class CredentialsEntityImpl implements CredentialsEntity {
    private final int id;
    private String access;
    private String refresh;
    private LocalDateTime expiration;
    private UserEntity user;

    public CredentialsEntityImpl(int id, @NotNull UserEntity user) {
        this.id = id;
        this.user = user;
    }

    public CredentialsEntityImpl(int id, String access, String refresh, LocalDateTime expiration, UserEntity user) {
        this.id = id;
        this.access = access;
        this.refresh = refresh;
        this.expiration = expiration;
        this.user = user;
    }

    @Override
    public @NotNull String accessToken() {
        return access;
    }

    @Override
    public void accessToken(@NotNull String value) {
        this.access = value;
    }

    @Override
    public @NotNull String refreshToken() {
        return refresh;
    }

    @Override
    public void refreshToken(@NotNull String value) {
        this.refresh = value;
    }

    @Override
    public @NotNull LocalDateTime expiresAt() {
        return expiration;
    }

    @Override
    public void expiresAt(@NotNull LocalDateTime value) {
        this.expiration = value;
    }

    @Override
    public @NotNull UserEntity user() {
        return user;
    }

    @Override
    public Integer id() {
        return id;
    }

    @Override
    public void user(@NotNull UserEntity value) {
        user = value;
    }
}
