package it.futurecraft.sorrentino.configuration.structures;

import org.jetbrains.annotations.NotNull;

public record Database(@NotNull String host, int port, @NotNull String database, @NotNull String username,
                       @NotNull String password, @NotNull Driver driver) {
    public enum Driver {
        MYSQL("com.mysql.cj.jdbc.Driver"),
        SQLITE("org.sqlite.JDBC"),
        MARIADB("org.mariadb.jdbc.Driver"),
        POSTGRESQL("org.postgresql.Driver");


        private final String classpath;

        Driver(String classpath) {
            this.classpath = classpath;
        }

        @Override
        public String toString() {
            return classpath;
        }
    }

    @NotNull
    public String url() {
        if (driver == Driver.SQLITE)
            return String.format("jdbc:sqlite:%s", database);

        return String.format("jdbc:%s://%s:%d/%s", driver, host, port, database);
    }
}
