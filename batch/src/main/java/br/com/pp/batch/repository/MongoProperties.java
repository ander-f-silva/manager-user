package br.com.pp.batch.repository;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class MongoProperties {
    private Properties properties = new Properties();

    private static HashMap<String, String> environment = new HashMap<>();

    static {
        environment.put("default", "mongodb.properties");
        environment.put("local", "mongodb-local.properties");
    }

    public MongoProperties() throws Exception {
        String paramEnvironment = System.getProperty("env");
        String fileProperties = environment.get(paramEnvironment == null ? "default" : paramEnvironment);
        InputStream input = getClass().getClassLoader().getResourceAsStream(fileProperties);
        properties.load(input);
    }

    public String getValue(final String key) {
        return properties.getProperty(key);
    }
}