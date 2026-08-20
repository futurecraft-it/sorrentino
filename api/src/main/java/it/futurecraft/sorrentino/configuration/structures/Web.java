package it.futurecraft.sorrentino.configuration.structures;

import org.jetbrains.annotations.NotNull;

public record Web(@NotNull String hostname, int port) {
}
