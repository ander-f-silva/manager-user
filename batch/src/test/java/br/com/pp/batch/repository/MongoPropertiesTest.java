package br.com.pp.batch.repository;


import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MongoPropertiesTest {
    
    @Test
    public void shouldReturnNullValueNotFound() throws Exception {
        MongoProperties mongoProperties = new MongoProperties();
        String result = mongoProperties.getValue("mongodb.uri");
        assertNull(result);
    }

    @Test
    public void shouldReturnValueFoundWithSuccess() throws Exception {
        MongoProperties mongoProperties = new MongoProperties();
        String result = mongoProperties.getValue("mongodb.database");
        assertEquals("user-db", result);
    }
}