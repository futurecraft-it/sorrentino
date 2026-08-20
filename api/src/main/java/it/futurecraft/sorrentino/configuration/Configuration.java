package it.futurecraft.sorrentino.configuration;

import it.futurecraft.sorrentino.configuration.structures.AppCredentials;
import it.futurecraft.sorrentino.configuration.structures.Database;
import it.futurecraft.sorrentino.configuration.structures.Web;

public interface Configuration {
    AppCredentials credentials();

    Web web();

    Database database();
}
