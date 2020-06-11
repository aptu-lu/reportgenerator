package Writers;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TxtWriter {
    String fileName;

    public TxtWriter(String fileName) {
        this.fileName = fileName;
    }

    public void write(String result) {
        try {
            FileWriter fileWriter = new FileWriter(fileName, StandardCharsets.UTF_16, false);
            fileWriter.write(result);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
