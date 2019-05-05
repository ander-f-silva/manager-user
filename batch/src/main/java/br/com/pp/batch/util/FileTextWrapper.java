package br.com.pp.batch.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileTextWrapper {

    private BufferedReader reader;

    public FileTextWrapper(String path) throws IOException {
        reader = Files.newBufferedReader(Paths.get(path));
    }

    public List<String> reader() throws IOException {
        List<String> lines = new ArrayList<>();
        while (reader.ready()) {
            lines.add(reader.readLine());
        }
        reader.close();
        return lines;
    }
}
