package it.futurecraft.sorrentino.database.entities;

import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Entity
@Table(name = "user")
public interface User extends it.futurecraft.sorrentino.database.Entity<UUID> {
    @Id
    UUID id();

    String name();

    String twitchLogin();

    int twitchId();

    boolean streamer();

    @OneToOne
    @NotNull Credentials credentials();
}
