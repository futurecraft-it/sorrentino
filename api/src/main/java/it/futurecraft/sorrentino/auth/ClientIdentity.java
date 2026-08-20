package it.futurecraft.sorrentino.auth;

public record ClientIdentity(
        String id,
        String secret
) {
}
