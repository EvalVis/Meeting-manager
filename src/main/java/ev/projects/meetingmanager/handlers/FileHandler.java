package ev.projects.meetingmanager.handlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {

    File dataFile;

    public FileHandler() {
        createFileIfNotFound();
    }

    private void createFileIfNotFound() {
        dataFile = new File("data/meetings.json");
        if(!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                writeToFile("[]");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeToFile(String data) {
        try(FileWriter fileWriter = new FileWriter(dataFile)) {
            fileWriter.write(data);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
