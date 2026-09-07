package main;

public class GitHubEvent {
    public String type;
    public Repo repo;

    static class Repo {
        public String name;
    }

}
