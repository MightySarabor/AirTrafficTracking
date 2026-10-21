package de.mightysarabor.api_utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.mightysarabor.parser.JSONParser;
import de.mightysarabor.records.HeaderRecord;
import de.mightysarabor.records.ResponseRecord;
import de.mightysarabor.records.TokenRecord;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

public class AccessTokenService {

    private final APIClient client;
    private final String header;
    private final String body;
    private final String url;
    private final JSONParser parser;

    public AccessTokenService(String header, APIClient Client, String url, String body, JSONParser parser) {
        this.header = header;
        this.client = Client;
        this.body = body;
        this.url = url;
        this.parser = parser;

    }

    private String getToken() throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ResponseRecord postResponse = client.post(url, body, header);
        TokenRecord tokenRecord = mapper.readValue(postResponse.responseBody(), TokenRecord.class);
        return tokenRecord.access_token();
    }

    public HeaderRecord getAuthorizationHeader() throws IOException, InterruptedException {
        return new HeaderRecord("Authorization", "Bearer " + getToken());
    }

    public long getRemainingTime(String accessToken) throws JsonProcessingException {
        long now = Instant.now().getEpochSecond();
        String expiresAt = parser.parseToTree(decodeJwtPart(accessToken, 1)).get("exp").asText();
        return Long.parseLong(expiresAt) - now;
    }

    public String decodeJwtPart(String jwt, int part) {
        String[] parts = jwt.trim().split("\\.");
        byte[] decoded = Base64.getUrlDecoder().decode(parts[part]);
        return new String(decoded, StandardCharsets.UTF_8);
    }
}
