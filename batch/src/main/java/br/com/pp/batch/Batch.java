package br.com.pp.batch;

import br.com.pp.batch.repository.MongoRepository;
import br.com.pp.loader.util.FileCsvWrapper;
import br.com.pp.loader.util.FileTextWrapper;
import org.bson.Document;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;

public class Batch {

    private List<String> rankOne;
    private List<String> rankTwo;
    private FileCsvWrapper fileCsv;
    private MongoRepository mongoRepository;

    public Batch(Map<String, String> mapPaths, MongoRepository mongoRepository) throws Exception {
        this.mongoRepository = mongoRepository;
        fileCsv = new FileCsvWrapper(mapPaths.get("fileUser"));
        rankOne = new FileTextWrapper(mapPaths.get("rankOne")).reader();
        rankTwo = new FileTextWrapper(mapPaths.get("rankTwo")).reader();
    }

    public void execute() throws Exception{
        List<Document> documents = new LinkedList<>();
        saveData(documents);
        toFinish(documents);
    }

    private void saveData(List<Document> documents) throws IOException {
        String[] line;
        String id;
        while ((line = fileCsv.reader()) != null) {
            id = line[0];
            documents.add(new Document("_id", id).append("name", line[1]).append("userName", line[2]).append("rank", getRank(id)).append("createDate", now()));
            executeBatch(documents);
        }
    }

    private int getRank(final String id) {
        return rankOne.contains(id) ? 1 : rankTwo.contains(id) ? 2 : 3;
    }

    private void executeBatch(List<Document> documents) {
        if (documents.size() == 1000) {
            mongoRepository.addBatch(documents);
            documents.clear();
        }
    }

    private void toFinish(List<Document> documents) throws IOException {
        fileCsv.close();
        if (!documents.isEmpty())
            mongoRepository.addBatch(documents);
        mongoRepository.close();
    }
}
