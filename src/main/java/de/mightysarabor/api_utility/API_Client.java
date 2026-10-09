package de.mightysarabor.api_utility;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

public class API_Client {


    private final HttpClient client;

    public API_Client() {

        client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();


    }

    private HttpResponse<String> getResponse(HttpRequest request ) throws IOException, InterruptedException {

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        if (response.statusCode() >= 200 &&
                response.statusCode() < 300) {

            return response;
        }
        throw new IOException(
                "HTTP " +
                        response.statusCode() +
                        ": " +
                        response.body()
        );
    }

    public String get(
            String url, Map<String, String> headers
    ) throws IOException, InterruptedException {


        Map.Entry<String, String> entry = headers.entrySet().iterator().next();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(20))
                .header(entry.getKey(), entry.getValue())
                .GET()
                .build();


        HttpResponse<String> response = getResponse(request);

        return response.headers().toString();
    }

    public String post(
            String url,
            String json,
            String headerValue
    ) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(20))
                .header(
                        "Accept",
                        "application/json"
                )
                .header(
                        "Content-Type",
                        headerValue
                )
                .POST(
                        HttpRequest.BodyPublishers.ofString(json)
                )
                .build();

        HttpResponse<String> response = getResponse(request);
        return response.body();
    }
}
