package it.futurecraft.sorrentino.database.enities;

import com.github.twitch4j.helix.domain.User;
import it.futurecraft.sorrentino.database.Entity;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public interface CredentialsEntity extends Entity<Integer> {
    @NotNull String accessToken();

    void accessToken(@NotNull String value);

    @NotNull String refreshToken();

    void refreshToken(@NotNull String value);

    @NotNull LocalDateTime expiresAt();

    void expiresAt(@NotNull LocalDateTime value);

    default int expiresIn() {
        LocalDateTime expiration = expiresAt();
        return LocalDateTime.now().compareTo(expiration);
    }

    @NotNull UserEntity user();

    void user(@NotNull UserEntity value);
}
