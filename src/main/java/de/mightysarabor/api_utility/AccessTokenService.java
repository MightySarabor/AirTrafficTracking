package de.mightysarabor.api_utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.mightysarabor.parser.JSONParser;
import de.mightysarabor.records.HeaderRecord;
import de.mightysarabor.records.ResponseRecord;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

public class AccessTokenService {

    private API_Client client;
    private String header;
    private String body;
    private String url;
    private JSONParser parser;

    public AccessTokenService(String header, API_Client Client, String url, String body, JSONParser parser) {
        this.header = header;
        this.client = Client;
        this.body = body;
        this.url = url;
        this.parser = parser;

    }

    public String getFlightInfo() throws IOException, InterruptedException {

        ResponseRecord postResponse = client.post(url, body, header);
        String accessToken = parser.parseToTree(postResponse.responseBody()).get("access_token").asText();
        long now = Instant.now().getEpochSecond();
        now = Instant.now().getEpochSecond();
        return accessToken;
    }

    public long getRemainingTime(String accessToken) throws JsonProcessingException {
        long now = Instant.now().getEpochSecond();
        String expiresAt = parser.parseToTree(decodeJwtPart(accessToken, 1)).get("exp").asText();
        long remaining = Long.parseLong(expiresAt) - now;
        System.out.println("Noch gültig: " + remaining / 60 + " min " + remaining % 60 + " s");

        return remaining;
    }

    public String decodeJwtPart(String jwt, int part) {
        String[] parts = jwt.trim().split("\\.");
        byte[] decoded = Base64.getUrlDecoder().decode(parts[part]);
        return new String(decoded, StandardCharsets.UTF_8);
    }
}
