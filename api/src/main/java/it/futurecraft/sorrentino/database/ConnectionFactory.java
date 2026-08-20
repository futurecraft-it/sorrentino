package it.futurecraft.sorrentino.database;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {
    @NotNull Connection create() throws SQLException;
}
