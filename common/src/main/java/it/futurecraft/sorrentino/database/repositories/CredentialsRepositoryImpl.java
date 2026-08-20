package it.futurecraft.sorrentino.database.repositories;

import com.google.inject.Inject;
import it.futurecraft.sorrentino.database.ConnectionFactory;
import it.futurecraft.sorrentino.database.enities.CredentialsEntity;
import it.futurecraft.sorrentino.database.enities.UserEntity;
import it.futurecraft.sorrentino.database.enitities.CredentialsEntityImpl;
import it.futurecraft.sorrentino.database.enitities.UserEntityImpl;
import it.futurecraft.sorrentino.utils.UUIDUtils;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class CredentialsRepositoryImpl implements CredentialsRepository {
    private final ConnectionFactory connectionFactory;

    @Inject
    public CredentialsRepositoryImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public CompletableFuture<List<CredentialsEntity>> findByUser(@NotNull UserEntity user) {
        return null;
    }

    @Override
    public CompletableFuture<Void> commit() {
        final @Language("SQL") String query = """
            CREATE TABLE IF NOT EXISTS `credential` (
                `id` INTEGER NOT NULL AUTO_INCREMENT,
                `user_id` BINARY(16) NOT NULL,
                `access_token` VARCHAR(255) NOT NULL,
                `refresh_token` VARCHAR(255) NOT NULL,
                `expires_at` DATETIME NOT NULL,
                PRIMARY KEY (`id`),
                FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
            );
        """;

        return CompletableFuture.runAsync(() -> {
            try(Connection conn = connectionFactory.create(); Statement stmt = conn.createStatement()) {
                stmt.execute(query);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public @NotNull CompletableFuture<List<CredentialsEntity>> findAll() {
        final @Language("SQL") String query = "SELECT * FROM `credential` INNER JOIN `user` ON `credential`.`user_id` = `user`.`id`;";

        return CompletableFuture.supplyAsync(() -> {
            try(Connection conn = connectionFactory.create(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                List<CredentialsEntity> credentials = new ArrayList<>();
                while(rs.next()) {
                    credentials.add(new CredentialsEntityImpl(
                        rs.getInt("id"),
                        rs.getString("access_token"),
                        rs.getString("refresh_token"),
                        rs.getTimestamp("expires_at").toLocalDateTime(),
                        new UserEntityImpl(
                                UUIDUtils.fromBytes(rs.getBytes("user_id")),
                                rs.getString("display_name"),
                                rs.getString("twitch_username"),
                                rs.getInt("twitch_id"),
                                rs.getBoolean("is_streamer")
                        )
                    ));
                }
                return credentials;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Optional<CredentialsEntity>> findById(@NotNull Integer integer) {
        final @Language("SQL") String query = "SELECT * FROM `credential` INNER JOIN `user` ON `credential`.`user_id` = `user`.`id` WHERE `credential`.`id` = ?;";

        return CompletableFuture.supplyAsync(() -> {
            try (Connection conn = connectionFactory.create(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, integer);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) return Optional.empty();

                    CredentialsEntity credentials = new CredentialsEntityImpl(
                            rs.getInt("id"),
                            rs.getString("access_token"),
                            rs.getString("refresh_token"),
                            rs.getTimestamp("expires_at").toLocalDateTime(),
                            new UserEntityImpl(
                                    UUIDUtils.fromBytes(rs.getBytes("user_id")),
                                    rs.getString("display_name"),
                                    rs.getString("twitch_username"),
                                    rs.getInt("twitch_id"),
                                    rs.getBoolean("is_streamer")
                            )
                    );

                    return Optional.of(credentials);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<CredentialsEntity> create(@NotNull Integer integer, @NotNull Consumer<CredentialsEntity> consumer) {
        final @Language("SQL") String query = "INSERT INTO `credential`(`access_token`, `refresh_token`, `expires_at`, `user_id`) VALUES (?, ?, ?, ?);";

        return CompletableFuture.supplyAsync(() -> {
            try (Connection conn = connectionFactory.create(); PreparedStatement stmt = conn.prepareStatement(query)) {
                CredentialsEntityImpl credentials = new CredentialsEntityImpl(integer, null);

                consumer.accept(credentials);

                stmt.setString(1, credentials.accessToken());
                stmt.setString(2, credentials.refreshToken());
                stmt.setTimestamp(3, Timestamp.valueOf(credentials.expiresAt()));
                stmt.setBytes(4, UUIDUtils.toBytes(credentials.user().id()));

                int rowsAffected = stmt.executeUpdate();
                return credentials;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Optional<CredentialsEntity>> update(@NotNull Integer integer, @NotNull Consumer<CredentialsEntity> consumer) {
        final @Language("SQL") String query = "UPDATE `credential` SET `access_token` = ?, `refresh_token` = ?, `expires_at` = ?, `user_id` = ? WHERE `id` = ?;";

        return findById(integer).thenCompose((credentials) -> {
            try(Connection conn = connectionFactory.create(); PreparedStatement stmt = conn.prepareStatement(query)) {
                if (credentials.isEmpty()) return CompletableFuture.completedFuture(Optional.empty());
                CredentialsEntity credentialsEntity = credentials.get();

                consumer.accept(credentialsEntity);

                stmt.setString(1, credentialsEntity.accessToken());
                stmt.setString(2, credentialsEntity.refreshToken());
                stmt.setTimestamp(3, Timestamp.valueOf(credentialsEntity.expiresAt()));
                stmt.setBytes(4, UUIDUtils.toBytes(credentialsEntity.user().id()));
                stmt.setInt(5, integer);

                int rowsAffected = stmt.executeUpdate();
                return CompletableFuture.completedFuture(Optional.of(credentialsEntity));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> delete(@NotNull Integer integer) {
        final @Language("SQL") String query = "DELETE FROM `credential` WHERE `id` = ?;";

        return CompletableFuture.supplyAsync(() -> {
            try(Connection conn = connectionFactory.create(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, integer);

                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
