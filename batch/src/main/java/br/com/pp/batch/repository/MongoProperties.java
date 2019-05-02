package br.com.pp.batch.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class MongoProperties {
    private static String PATH_MONGO_PROPERTIES = "src/main/resources/mongodb.properties";

    private Properties properties = new Properties();

    public MongoProperties() throws IOException {
        properties.load(Files.newInputStream(Paths.get(PATH_MONGO_PROPERTIES)));
    }

    public String getValue(final String key) {
        return properties.getProperty(key);
    }
}