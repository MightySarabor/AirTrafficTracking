package de.mightysarabor.api_utility;

import de.mightysarabor.parser.JSONParser;
import de.mightysarabor.records.HeaderRecord;
import de.mightysarabor.records.ResponseRecord;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

public class OpenSkyAPIService {

    private API_Client client;
    private String header;
    private String body;
    private String url;
    private JSONParser parser;


    public OpenSkyAPIService(String header, API_Client Client, String url, String body, JSONParser parser) {
        this.header = header;
        this.client = Client;
        this.body = body;
        this.url = url;
        this.parser = parser;

    }


    public void getFlightInfo() throws IOException, InterruptedException {

        ResponseRecord postResponse = client.post(url, body, header);
        String accessToken = parser.parseToTree(postResponse.responseBody()).get("access_token").asText();

        String expiresAt = parser.parseToTree(decodeJwtPart(accessToken, 1)).get("exp").asText();
        long now = Instant.now().getEpochSecond();
        long remaining = Long.parseLong(expiresAt) - now;


        while (true) {
            if (remaining < 100) {
                System.out.println("NEW TOKEN LETS GO");

                postResponse = client.post(url, body, header);
                accessToken = parser.parseToTree(postResponse.responseBody()).get("access_token").asText();
                expiresAt = parser.parseToTree(decodeJwtPart(accessToken, 1)).get("exp").asText();
            }
            now = Instant.now().getEpochSecond();

            remaining = Long.parseLong(expiresAt) - now;

            //System.out.println(postResponse.responseBody());
            System.out.println("Noch gültig: " + remaining / 60 + " min " + remaining % 60 + " s");

            //HeaderRecord getHeader = new HeaderRecord("Authorization", "Bearer " + accessToken);
            //ResponseRecord getResponse = client.get("https://opensky-network.org/api/states/all?_limit=1", getHeader);

            long stop = System.currentTimeMillis() + 5000; // mit sleep testen und gucken was besser läuft
            while(System.currentTimeMillis() <= stop)
            {
            }

        }
    }

    public String decodeJwtPart(String jwt, int part) {
        String[] parts = jwt.trim().split("\\.");
        byte[] decoded = Base64.getUrlDecoder().decode(parts[part]);
        return new String(decoded, StandardCharsets.UTF_8);
    }
}
