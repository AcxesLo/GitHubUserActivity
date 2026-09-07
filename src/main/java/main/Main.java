package main;

import api.GitHubAPI;
import com.google.gson.*;
import json.ConvertResponse;
import json.FileWriterGson;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static GitHubAPI gitHubAPI = new GitHubAPI();
    public static ConvertResponse convertResponse = new ConvertResponse();
    public static FileWriterGson fileWriterGson = new FileWriterGson();
    public static Scanner scanner = new Scanner(System.in);
    public static String githubUser = "";

    // TODO
    // ~~

    static void main(String[] args) throws IOException, InterruptedException {
        while (true) {
            try {
                githubUser = scanner.nextLine();
                if (githubUser.equalsIgnoreCase("exit")) {
                    break;
                }

                gitHubAPI.requestAPI(githubUser);
                convertResponse.formattedResponse();
                fileWriterGson.writeFile();

            } catch (IOException | InterruptedException | IllegalStateException | JsonSyntaxException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }
    }
}

