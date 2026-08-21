package it.futurecraft.sorrentino.database.entities;

import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity
public interface Credentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id();

    @NotNull String accessToken();

    @NotNull String refreshToken();

    @Column(name = "expires_at")
    @NotNull LocalDateTime expiration();

    @OneToOne
    @NotNull User user();
}
