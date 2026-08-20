package it.futurecraft.sorrentino.configuration.structures;

import org.jetbrains.annotations.NotNull;

public record AppCredentials(@NotNull String id, @NotNull String secret) {
}
