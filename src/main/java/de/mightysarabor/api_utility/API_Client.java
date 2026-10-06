package de.mightysarabor.api_utility;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class API_Client
{

    ObjectMapper mapper = new ObjectMapper()
            .configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false
            );
    HttpClient client = HttpClient.newHttpClient();

    HttpRequest request;

    HttpResponse<String> response;


    public void makeRequest(String endPoint) throws IOException, InterruptedException {
        request = HttpRequest.newBuilder()
                .uri(URI.create(
                        endPoint
                ))
                .GET()
                .build();

       response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
       System.out.println(response.body());
    }
}
