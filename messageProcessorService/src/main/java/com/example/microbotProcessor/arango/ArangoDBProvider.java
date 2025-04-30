package com.example.microbotProcessor.arango;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.Protocol;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import io.micronaut.context.annotation.Value;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Getter
@Factory
public class ArangoDBProvider {

    private final ArangoDB arangoDB;
    private final String databaseName;
    private final ArangoDatabase database;

    public ArangoDBProvider(
            @Value("${arangodb.host}") String host,
            @Value("${arangodb.port}") int port,
            @Value("${arangodb.user}") String user,
            @Value("${arangodb.password}") String password,
            @Value("${arangodb.database}") String database
    ){
        this.arangoDB = new ArangoDB.Builder()
                .host(host, port)
                .user(user)
                .password(password)
                .useProtocol(Protocol.HTTP_JSON)
                .build();
        this.databaseName = database;
        if (!arangoDB.db(databaseName).exists()) {
            arangoDB.createDatabase(databaseName);
            System.out.println("Created ArangoDB database " + databaseName);
        }


        this.database = arangoDB.db(databaseName);
    }
    @Singleton
    public ArangoDatabase getDatabase(){
        return arangoDB.db(databaseName);
    }

}
