package it.futurecraft.sorrentino.database;

import com.google.inject.Inject;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import it.futurecraft.sorrentino.configuration.Configuration;
import it.futurecraft.sorrentino.configuration.structures.Database;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactoryImpl implements ConnectionFactory{
    private final Database configuration;

    private final HikariDataSource ds;

    @Inject
    public ConnectionFactoryImpl(@NotNull Configuration configuration) {
        this.configuration = configuration.database();


        HikariConfig conf = new HikariConfig();
        conf.setJdbcUrl(this.configuration.url());
        conf.setDriverClassName(this.configuration.driver().toString());
        conf.setUsername(this.configuration.username());
        conf.setPassword(this.configuration.password());
        conf.setMaximumPoolSize(6);
        conf.setReadOnly(false);
        conf.setTransactionIsolation("TRANSACTION_SERIALIZABLE");

        this.ds = new HikariDataSource(conf);
    }

    @Override
    public @NotNull Connection create() throws SQLException {
        return ds.getConnection();
    }
}
