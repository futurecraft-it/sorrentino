package it.futurecraft.sorrentino.database;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface Repository<ID, E extends Entity<ID>> {
    CompletableFuture<Void> commit();

    @NotNull CompletableFuture<List<E>> findAll();

    CompletableFuture<Optional<E>> findById(@NotNull ID id);

    CompletableFuture<E> create(@NotNull ID id, @NotNull Consumer<E> consumer);

    CompletableFuture<Optional<E>> update(@NotNull ID id, @NotNull Consumer<E> consumer);

    CompletableFuture<Boolean> delete(@NotNull ID id);
}
