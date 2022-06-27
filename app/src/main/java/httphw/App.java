package httphw;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class App {

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name: ");
        String input = sc.nextLine();
        String json = buildJson(input);

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create("https://dummy.restapiexample.com/api/v1/create"))
                .setHeader("User-Agent", "SomeApp")
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int responseCode = response.statusCode();
        printResult(responseCode);

    }

    public static String buildJson(String input) {
        String json = new StringBuilder().append("{")
                .append("\"name\":\"")
                .append(input)
                .append("\",")
                .append("\"salary\":\"777\",")
                .append("\"age\":\"55\"")
                .append("}").toString();

        return json;
    }

    public static void printResult(int responseCode) {
        if (responseCode == 200) {
            System.out.println("----------SUCCESS----------");
            System.out.println("-------RESPONSE BODY-------");
            System.out.println(response.body());
        }
        if (responseCode == 429) {
            //Специально не выводил response.body()
            System.out.println("------TOO MANY REQUESTS------");
            System.out.println("-------TRY AGAIN LATER-------");
        }
        else {
            System.out.println("smth wrong");
        }
    }
}
