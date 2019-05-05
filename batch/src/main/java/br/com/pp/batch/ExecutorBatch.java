package br.com.pp.batch;

import br.com.pp.batch.repository.MongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class ExecutorBatch {

    private static Logger log = LoggerFactory.getLogger(ExecutorBatch.class);

    private static MongoRepository mongoRepository;

    static {
        try {
            mongoRepository = new MongoRepository();
        } catch (Exception ex) {
            log.error("Error of connection", ex);
        }
    }

    public static void main(String[] args) throws Exception {
        log.info("Start execute batch. Time: {}", new Date());

        log.info("File CSV. Param: {}",args[0]);
        log.info("File TXT 1. Param: {}",args[1]);
        log.info("File TXT 2. Param: {}",args[2]);

        Map<String,String> mapParameters = new HashMap<>();
        mapParameters.put("fileUser",args[0]);
        mapParameters.put("rankOne",args[1]);
        mapParameters.put("rankTwo",args[2]);

        Batch userBatch = new Batch(mapParameters, mongoRepository);
        userBatch.execute();

        log.info("End execute batch. Time: {}", new Date());
    }
}
