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
                .uri(URI.create("https://api.github.com/users/" + githubUser + "/events"))
                .GET()
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status: " + response.statusCode());
    }

    public HttpResponse<String> getResponse() {
        return response;
    }
}
