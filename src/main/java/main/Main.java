package main;

import api.GitHubAPI;
import com.google.gson.*;
import json.ConvertResponse;
import json.FileWriterGson;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static GitHubAPI gitHubAPI = new GitHubAPI();
    public static ConvertResponse convertResponse = new ConvertResponse();
    public static FileWriterGson fileWriterGson = new FileWriterGson();
    public static Scanner scanner = new Scanner(System.in);
    public static String[] parts;
    public static String githubUser = "";
    public static String joinedInput;

    // TODO
    // ~~

    static void main(String[] args) throws IOException, InterruptedException {
        while (true) {
            try {
                System.out.print("> ");

                githubUser = scanner.nextLine();
                parts = githubUser.split("\\s+");

                if (githubUser.equalsIgnoreCase("exit")) {
                    break;
                }

                if (parts[0].equalsIgnoreCase("user-activity")) {
                    joinedInput = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
                    joinedInput = joinedInput.replaceAll("^\"|\"$", "");
                    githubUser = joinedInput;

                    gitHubAPI.requestAPI(githubUser);
                    convertResponse.formattedResponse();
                    fileWriterGson.writeFile();

                } else {
                    System.out.println("Wrong command.");
                }



            } catch (IOException | InterruptedException | IllegalStateException | JsonSyntaxException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

