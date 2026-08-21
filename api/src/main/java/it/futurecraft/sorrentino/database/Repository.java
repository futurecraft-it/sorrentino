package it.futurecraft.sorrentino.database;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface Repository<ID, E extends Entity<ID>> {
    CompletableFuture<List<E>> findAll();

    CompletableFuture<Optional<E>> findById(@NotNull ID id);

    CompletableFuture<Boolean> save(@NotNull E entity);

    CompletableFuture<Boolean> delete(@NotNull E entity);
}
