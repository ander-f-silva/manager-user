package br.com.pp.batch;

import java.util.*;
import java.util.logging.Logger;

public class ExecutorBatch {


    public static void main(String[] args) throws Exception {
        System.out.println("Start " + new Date());

        Map<String,String> mapPaths = new HashMap<>();
        mapPaths.put("fileUser",args[0]);
        mapPaths.put("rankOne",args[1]);
        mapPaths.put("rankTwo",args[2]);

        UserBatch userBatch = new UserBatch(mapPaths);
        userBatch.execute();

        System.out.println("End " + new Date());
    }
}
