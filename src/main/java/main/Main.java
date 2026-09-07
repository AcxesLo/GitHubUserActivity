package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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

    // project concept
    /*
    The application should run from the command line,
    accept the GitHub username as an argument,
    fetch the user's recent activity using the GitHub API,
    and display it in the terminal.
    */


    // bash

    // # provide the GitHub username as an argument
    // github-activity <username>

    // # Fetch the recent activity of the specified GitHub user using the GitHub API.
    // # You can use the following endpoint to fetch the user's activity:
    // - JavaScript
    // # https://api.github.com/users/<username>/events
    // # Example: https://api.github.com/users/kamranahmedse/events

    // # Display the fetched activity in the terminal
    // - JavaScript
    // # Output:
    // - Pushed 3 commits to kamranahmedse/developer-roadmap
    // - Opened a new issue in kamranahmedse/developer-roadmap
    // - Starred kamranahmedse/developer-roadmap
    // - ...

    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String githubUser = "";

        githubUser = scanner.nextLine();

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/users/" + githubUser + "/events"))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + response.statusCode());

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

            try {
                FileWriter fileWriter = new FileWriter(file, false);
                gson.toJson(jsonElement, fileWriter);
                System.out.println("Data written to file.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

