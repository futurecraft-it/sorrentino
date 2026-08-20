package it.futurecraft.sorrentino.database.repositories;

import com.google.inject.Inject;
import it.futurecraft.sorrentino.database.ConnectionFactory;
import it.futurecraft.sorrentino.database.enities.UserEntity;
import it.futurecraft.sorrentino.database.enitities.UserEntityImpl;
import it.futurecraft.sorrentino.utils.UUIDUtils;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class UserRepositoryImpl implements UserRepository {
    private final ConnectionFactory connectionFactory;

    @Inject
    public UserRepositoryImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public CompletableFuture<Void> commit() {
        final @Language("SQL") String query = """
            CREATE TABLE IF NOT EXISTS `user` (
                `id` BINARY(16) NOT NULL,
                `display_name` VARCHAR(16) NOT NULL,
                `twitch_id` INTEGER NOT NULL DEFAULT -1,
                `twitch_username` VARCHAR(25),
                `is_streamer` BOOLEAN NOT NULL DEFAULT FALSE,
                PRIMARY KEY (`id`),
                UNIQUE KEY `uk_display_name` (`display_name`),
                UNIQUE KEY `uk_twitch_id` (`twitch_id`),
                UNIQUE KEY `uk_twitch_username` (`twitch_username`)
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
    public CompletableFuture<Optional<UserEntity>> findByTwitchId(int twitchId) {
        final @Language("SQL") String query = "SELECT * FROM `user` WHERE `twitch_id` = ?;";

        return CompletableFuture.supplyAsync(() -> {
            try(Connection conn = connectionFactory.create(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, twitchId);

                return getUserEntity(stmt);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Optional<UserEntity>> findByTwitchUsername(String twitchUsername) {
        final @Language("SQL") String query = "SELECT * FROM `user` WHERE `twitch_username` = ?;";

        return CompletableFuture.supplyAsync(() -> {
            try(Connection conn = connectionFactory.create(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, twitchUsername);

                return getUserEntity(stmt);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Optional<UserEntity>> findById(@NotNull UUID uuid) {
        final @Language("SQL") String query = "SELECT * FROM `user` WHERE `id` = ?;";

        return CompletableFuture.supplyAsync(() -> {
            try(Connection conn = connectionFactory.create(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setBytes(1, UUIDUtils.toBytes(uuid));

                return getUserEntity(stmt);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @NotNull
    private Optional<UserEntity> getUserEntity(PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.executeQuery()) {
            if (!rs.next()) return Optional.empty();

            byte[] bytes = rs.getBytes("id");
            UUID uuid = UUIDUtils.fromBytes(bytes);

            UserEntity user = new UserEntityImpl(
                    uuid,
                    rs.getString("display_name"),
                    rs.getString("twitch_username"),
                    rs.getInt("twitch_id"),
                    rs.getBoolean("is_streamer")
            );

            return Optional.of(user);
        }
    }

    @Override
    public CompletableFuture<List<UserEntity>> streamers() {
        final @Language("SQL") String query = "SELECT * FROM `user` WHERE `is_streamer` = TRUE;";

        return CompletableFuture.supplyAsync(() -> getUserEntities(query));
    }

    @Override
    public @NotNull CompletableFuture<List<UserEntity>> findAll() {
        final @Language("SQL") String query = "SELECT * FROM `user`;";

        return CompletableFuture.supplyAsync(() -> getUserEntities(query));
    }

    @NotNull
    private List<UserEntity> getUserEntities(String query) {
        try (Connection conn = connectionFactory.create(); PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                List<UserEntity> streamers = new ArrayList<>();

                while (rs.next()) {
                    byte[] bytes = rs.getBytes("id");
                    UUID uuid = UUIDUtils.fromBytes(bytes);

                    UserEntity user = new UserEntityImpl(
                            uuid,
                            rs.getString("display_name"),
                            rs.getString("twitch_username"),
                            rs.getInt("twitch_id"),
                            rs.getBoolean("is_streamer")
                    );

                    streamers.add(user);
                }

                return streamers;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompletableFuture<UserEntity> create(@NotNull UUID id, @NotNull Consumer<UserEntity> consumer) {
        final @Language("SQL") String query = "INSERT INTO `user` (`id`, `display_name`, `twitch_id`, `twitch_username`, `is_streamer`) VALUES (?, ?, ?, ?, ?);";

        return CompletableFuture.supplyAsync(() -> {
            try (Connection conn = connectionFactory.create(); PreparedStatement stmt = conn.prepareStatement(query)) {
                UserEntity user = new UserEntityImpl(id, "", null, 0, false);
                consumer.accept(user);

                stmt.setBytes(1, UUIDUtils.toBytes(user.id()));
                stmt.setString(2, user.displayName());
                stmt.setInt(3, user.twitchId());
                stmt.setString(4, user.twitchUsername());
                stmt.setBoolean(5, user.streamer());

                int rowsAffected = stmt.executeUpdate();
                return user;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Optional<UserEntity>> update(@NotNull UUID uuid, @NotNull Consumer<UserEntity> consumer) {
        final @Language("SQL") String query = "UPDATE `user` SET `display_name` = ?, `twitch_id` = ?, `twitch_username` = ?, `is_streamer` = ? WHERE `id` = ?;";

        return findById(uuid).thenCompose(optionalUser -> {
            try (Connection conn = connectionFactory.create(); PreparedStatement stmt = conn.prepareStatement(query)) {
                if (optionalUser.isEmpty()) return CompletableFuture.completedFuture(Optional.empty());

                UserEntity user = optionalUser.get();
                consumer.accept(user);

                stmt.setString(1, user.displayName());
                stmt.setInt(2, user.twitchId());
                stmt.setString(3, user.twitchUsername());
                stmt.setBoolean(4, user.streamer());
                stmt.setBytes(5, UUIDUtils.toBytes(user.id()));

                int rowsAffected = stmt.executeUpdate();
                return CompletableFuture.completedFuture(Optional.of(user));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> delete(@NotNull UUID uuid) {
        final @Language("SQL") String query = "DELETE FROM `user` WHERE `id` = ?;";

        return CompletableFuture.supplyAsync(() -> {
            try (Connection conn = connectionFactory.create(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setBytes(1, UUIDUtils.toBytes(uuid));

                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
