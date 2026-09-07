package api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GitHubAPI {
    public static HttpClient client = HttpClient.newHttpClient();
    public static HttpResponse<String> response;

    public void requestAPI(String githubUser) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/users/" + githubUser.strip() + "/events"))
                .GET()
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status: " + response.statusCode());

        String statusCode = String.valueOf(response.statusCode());
        if (!statusCode.equals("200")) {
            System.out.println("Request to the GitHub API failed. Error Code - " + response.statusCode());
        } else {
            System.out.println("StatusCode: " + response.statusCode());
        }
    }
}
