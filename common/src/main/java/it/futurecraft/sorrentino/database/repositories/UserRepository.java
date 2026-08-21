package it.futurecraft.sorrentino.database.repositories;

import com.google.inject.Inject;
import it.futurecraft.sorrentino.database.Repository;
import it.futurecraft.sorrentino.database.entities.User;
import org.babyfish.jimmer.sql.JSqlClient;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UserRepository implements Repository<UUID, User> {
    private final JSqlClient client;

    @Inject
    public UserRepository(JSqlClient client) {
        this.client = client;
    }

    @Override
    public CompletableFuture<List<User>> findAll() {
        return CompletableFuture.supplyAsync(() -> {
            // client.createQuery()

            return List.of();
        });
    }

    @Override
    public CompletableFuture<Optional<User>> findById(@NonNull UUID uuid) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> save(@NonNull User entity) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> delete(@NonNull User entity) {
        return null;
    }
}
