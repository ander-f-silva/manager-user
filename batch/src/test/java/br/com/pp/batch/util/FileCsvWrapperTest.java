package br.com.pp.batch.util;

import br.com.pp.batch.exception.FileEmptyException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileCsvWrapperTest {

    @Test(expected = IOException.class)
    public void shouldThrowExceptionForFileNotFound() throws Exception {
        FileCsvWrapper fileCsvWrapper = new FileCsvWrapper("src/test/user-not-found.csv");
        fileCsvWrapper.close();
    }

    @Test(expected = FileEmptyException.class)
    public void shouldThrowExceptionForFileEmpty() throws Exception {
        FileCsvWrapper fileCsvWrapper = new FileCsvWrapper("src/test/resources/users-empty.csv");
        fileCsvWrapper.close();
    }

    @Test
    public void shouldReadTheLinesFile() throws Exception {
        FileCsvWrapper fileCsvWrapper = new FileCsvWrapper("src/test/resources/users-test.csv");

        String[] expecteds = {
                "6d807dad-e949-4c8e-adf3-506d0816444c,Anderson Silva,anderson.silva",
                "78ccdcb1-2289-4ddb-8211-093e2c55963e,Bruno Mello,bruno.mello",
                "9b1e279b-6294-4ef0-9d88-42445b495aaa, Carmen Sandiego, camem.sandiego"
        };

        String[] lines;
        int index = 0;
        while ((lines = fileCsvWrapper.reader()) != null) {
            String[] expected =  expecteds[index].split(",");
            assertEquals(expected[0], lines[0]);
            assertEquals(expected[1], lines[1]);
            assertEquals(expected[2], lines[2]);
            index++;
        }
    }
}