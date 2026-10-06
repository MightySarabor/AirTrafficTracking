package de.mightysarabor.api_utility;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class API_Client {


    private final HttpClient client;

    public API_Client() {

        client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();


    }
    public String get(
            String url
    ) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(20))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() >= 200 &&
                response.statusCode() < 300) {

            return response.body();
        }

        throw new IOException(
                "HTTP " +
                        response.statusCode() +
                        ": " +
                        response.body()
        );
    }
}
