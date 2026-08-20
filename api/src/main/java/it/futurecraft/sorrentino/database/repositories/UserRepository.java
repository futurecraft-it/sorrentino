package it.futurecraft.sorrentino.database.repositories;

import it.futurecraft.sorrentino.database.Repository;
import it.futurecraft.sorrentino.database.enities.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface UserRepository extends Repository<UUID, UserEntity> {
    CompletableFuture<Optional<UserEntity>> findByTwitchId(int twitchId);

    CompletableFuture<Optional<UserEntity>> findByTwitchUsername(String twitchUsername);

    CompletableFuture<List<UserEntity>> streamers();
}
