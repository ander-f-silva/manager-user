package br.com.pp.batch.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.util.List;

public class MongoRepository {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoRepository() throws IOException {
        MongoProperties mongoProperties = new MongoProperties();

        String urlConnection = mongoProperties.getValue("mongodb.url.connection");
        String nameDataBase = mongoProperties.getValue("mongodb.database");
        String nameCollection = mongoProperties.getValue("mongodb.collection");

        mongoClient = MongoClients.create(urlConnection);
        database = mongoClient.getDatabase(nameDataBase);
        collection = database.getCollection(nameCollection);
    }

    public void addBatch(final List<Document> documents) {
        collection.insertMany(documents);
    }

    public void close() {
        mongoClient.close();
    }
}
