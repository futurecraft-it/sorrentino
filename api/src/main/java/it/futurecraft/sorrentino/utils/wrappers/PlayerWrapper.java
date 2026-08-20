package it.futurecraft.sorrentino.utils.wrappers;

import java.util.UUID;

public interface PlayerWrapper extends Wrapper {
    UUID uuid();

    String displayName();
}
