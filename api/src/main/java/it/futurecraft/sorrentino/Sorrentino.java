package it.futurecraft.sorrentino;

import it.futurecraft.sorrentino.configuration.Configuration;
import it.futurecraft.sorrentino.database.repositories.CredentialsRepository;
import it.futurecraft.sorrentino.database.repositories.UserRepository;

public interface Sorrentino {
    Configuration configuration();

    CredentialsRepository credentialsRepository();

    UserRepository userRepository();
}
