package main;

import com.google.gson.*;
import github.GitHubEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    // TODO
    //

    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String githubUser = "";

        githubUser = scanner.nextLine();

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/users/" + githubUser + "/events"))
                .GET()
                .build();

        while (true) {

            if (githubUser.equalsIgnoreCase("exit")){
                break;
            }

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("Status: " + response.statusCode());

                String statusCode = String.valueOf(response.statusCode());
                if (statusCode != "200") {
                    System.out.println("Request to the GitHub API failed. Error Code - " + response.statusCode());
                } else {
                    System.out.println("StatusCode: " + response.statusCode());
                }

                String operatingUser = System.getProperty("user.name");
                File file = new File("C:\\Users\\" + operatingUser + "\\Desktop\\githubrequest.json");

                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                // formats the long string from the GitHub API to a pretty-printed format
                JsonElement jsonElement = JsonParser.parseString(response.body());
                System.out.println("Data from the response: " + jsonElement);

                GitHubEvent[] events = gson.fromJson(response.body(), GitHubEvent[].class);

                Map<String, Integer> eventCounts = new HashMap<>();

                for (GitHubEvent event : events) {
                    String key = event.repo.name + " - " + event.type;
                    eventCounts.merge(key, 1, (a, b) -> Integer.sum(a, b));

                    if (event.type.equals("PushEvent")) {
                        if (event.payload.commits != null && !event.payload.commits.isEmpty()) {
                            System.out.println("Pushed " + event.payload.commits.size()
                                    + " commit(s) to " + event.repo.name);
                        }
                    }
                }

                for (Map.Entry<String, Integer> entry : eventCounts.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }

                // merging the events by the repos printing non-pushed commits

                try {
                    FileWriter fileWriter = new FileWriter(file, false);
                    gson.toJson(jsonElement, fileWriter);
                    System.out.println("Data written to file.");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException | InterruptedException | IllegalStateException | JsonSyntaxException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }
    }
}

