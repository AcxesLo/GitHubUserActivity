package json;

import api.GitHubAPI;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import github.GitHubEvent;

import java.util.HashMap;
import java.util.Map;

public class ConvertResponse {

    public void formattedResponse() {
        // formats the long string from the GitHub API to a pretty-printed format
        JsonElement jsonElement = JsonParser.parseString(GitHubAPI.response.body());
        System.out.println("Data from the response: " + jsonElement);

        GitHubEvent[] events = FileWriterGson.gson.fromJson(GitHubAPI.response.body(), GitHubEvent[].class);

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
    }
}
