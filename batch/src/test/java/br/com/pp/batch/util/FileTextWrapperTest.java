package br.com.pp.batch.util;

import br.com.pp.batch.exception.FileEmptyException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class FileTextWrapperTest {

    @Test(expected = IOException.class)
    public void shouldThrowExceptionForFileNotFound() throws Exception {
        new FileTextWrapper("src/test/list-not-found.txt");
    }

    @Test(expected = FileEmptyException.class)
    public void shouldThrowExceptionForFileEmpty() throws Exception {
        new FileTextWrapper("src/test/resources/list-empty.txt");
    }

    @Test
    public void shouldReadTheLinesFile() throws Exception {
        FileTextWrapper fileTextWrapper = new FileTextWrapper("src/test/resources/list-test.txt");

        String[] expecteds = {
                "6d807dad-e949-4c8e-adf3-506d0816444",
                "78ccdcb1-2289-4ddb-8211-093e2c55963e",
                "9b1e279b-6294-4ef0-9d88-42445b495aaa"
        };

        int index = 0;
        for (String line: fileTextWrapper.reader()){
            assertEquals(expecteds[index], line);
            index++;
        }
    }

}