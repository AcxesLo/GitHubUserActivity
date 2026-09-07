package main;

import java.util.List;

public class GitHubEvent {
    public String type;
    public Repo repo;
    public Payload payload;
    public CommitInfo commitInfo;

    static class Repo {
        public String name;
    }

    static class Payload {
        String ref;
        String head;
        List<CommitInfo> commits;
    }

    static class CommitInfo {
        String sha;
        String message;
    }

}
