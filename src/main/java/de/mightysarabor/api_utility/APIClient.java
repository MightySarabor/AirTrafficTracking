package de.mightysarabor.api_utility;

import de.mightysarabor.records.HeaderRecord;
import de.mightysarabor.records.ResponseRecord;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class APIClient {


    private final HttpClient client;

    public APIClient() {

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

    public ResponseRecord get(
            String url, HeaderRecord header
    ) throws IOException, InterruptedException {


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(20))
                .header(header.name(), header.value())
                .GET()
                .build();


        HttpResponse<String> response = getResponse(request);
        return new ResponseRecord(response.headers().toString(), response.body());
    }

    public ResponseRecord post(
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
        return new ResponseRecord(response.headers().toString(), response.body());
    }
}
