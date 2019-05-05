package br.com.pp.batch.util;

import br.com.pp.batch.exception.FileEmptyException;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileCsvWrapper {

    private BufferedReader reader;
    private CSVReader csvReader;

    public FileCsvWrapper(String path) throws IOException, FileEmptyException {
        reader = Files.newBufferedReader(Paths.get(path));

        if(reader.lines().count() == 0)
            throw new FileEmptyException("The file is empty");

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
