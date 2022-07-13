package ev.projects.meetingmanager.repositories;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public abstract class JsonFileRepository {

    private File dataFile = new File("data/meetings.json");

    private void createFileIfNotFound() {
        if(!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                writeToFile("[]");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected <T> void writeObjectToFile(T data, Type objectType) {
        String jsonData = new Gson().toJson(data, objectType);
        writeToFile(jsonData);
    }

    protected void writeToFile(String data) {
        createFileIfNotFound();
        try(FileWriter fileWriter = new FileWriter(dataFile)) {
            fileWriter.write(data);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    protected <T> List<T> getListFromFile(Type listType) {
        createFileIfNotFound();
        List<T> list = null;
        try(JsonReader reader = new JsonReader(new FileReader(dataFile))) {
            list = new Gson().fromJson(reader, listType);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
