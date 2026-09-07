package github;

import java.util.List;

public class GitHubEvent {
    public String type;
    public Repo repo;
    public Payload payload;
    public CommitInfo commitInfo;

    public static class Repo {
        public String name;
    }

    public static class Payload {
        public String ref;
        public String head;
        public List<CommitInfo> commits;
    }

    public static class CommitInfo {
        public String sha;
        public String message;
    }

}
