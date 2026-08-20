package it.futurecraft.sorrentino.database.repositories;

import it.futurecraft.sorrentino.database.Repository;
import it.futurecraft.sorrentino.database.enities.CredentialsEntity;
import it.futurecraft.sorrentino.database.enities.UserEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CredentialsRepository extends Repository<Integer, CredentialsEntity> {
    CompletableFuture<List<CredentialsEntity>> findByUser(@NotNull UserEntity user);
}
