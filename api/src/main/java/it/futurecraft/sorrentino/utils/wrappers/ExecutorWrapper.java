package it.futurecraft.sorrentino.utils.wrappers;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public interface ExecutorWrapper extends Wrapper {
    CompletableFuture<Void> execute(Runnable task);

    <T> CompletableFuture<T> execute(Supplier<T> task);

    <T> CompletableFuture<T> fail(Throwable e);
}
