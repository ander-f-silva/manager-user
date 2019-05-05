package br.com.pp.batch.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class FileCsvWrapper {

    private BufferedReader reader;
    private CSVReader csvReader;

    public FileCsvWrapper(String path) throws IOException {
        reader = Files.newBufferedReader(Paths.get(path));
        csvReader = new CSVReaderBuilder(reader).build();
    }

    public String[] reader() throws IOException {
        return csvReader.readNext();
    }

    public void close() throws IOException {
        if(csvReader != null)
            csvReader.close();

        reader.close();
    }
}
