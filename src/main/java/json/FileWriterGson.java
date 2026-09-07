package json;

import api.GitHubAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;

public class FileWriterGson {
        public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void writeFile() {
        String operatingUser = System.getProperty("user.name");
        JsonElement jsonElement = JsonParser.parseString(GitHubAPI.response.body());

        File file = new File("C:\\Users\\" + operatingUser + "\\Desktop\\githubrequest.json");

        try {
            java.io.FileWriter fileWriter = new java.io.FileWriter(file, false);
            gson.toJson(jsonElement, fileWriter);
            System.out.println("Data written to file.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
