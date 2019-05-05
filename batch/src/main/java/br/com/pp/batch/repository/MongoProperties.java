package br.com.pp.batch.repository;

import br.com.pp.batch.ExecutorBatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class MongoProperties {
    private static Logger log = LoggerFactory.getLogger(ExecutorBatch.class);

    private Properties properties = new Properties();

    private static HashMap<String, String> environment = new HashMap<>();

    static {
        environment.put("default", "mongodb.properties");
        environment.put("local", "mongodb-local.properties");
    }

    public MongoProperties() throws IllegalArgumentException {
        try {
            String paramEnvironment = System.getProperty("env");
            String fileProperties = environment.get(paramEnvironment == null ? "default" : paramEnvironment);
            InputStream input = getClass().getClassLoader().getResourceAsStream(fileProperties);
            properties.load(input);
        } catch (Exception ex) {
            log.error("Error loader properties", ex);
            throw new IllegalArgumentException("Error loader file properties");
        }

    }

    public String getValue(final String key) {
        return properties.getProperty(key);
    }
}