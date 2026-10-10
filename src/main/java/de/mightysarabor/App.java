package de.mightysarabor;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.mightysarabor.api_utility.OpenSkyAPIService;
import de.mightysarabor.api_utility.API_Client;
import de.mightysarabor.parser.JSONParser;
import de.mightysarabor.records.CreatePostRequest;
import de.mightysarabor.records.HeaderRecord;
import de.mightysarabor.records.PracticeRecord;
import de.mightysarabor.records.ResponseRecord;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) throws IOException, InterruptedException {
        API_Client client = new API_Client();
        JSONParser parser = new JSONParser();
        ObjectMapper mapper = new ObjectMapper();
        String url = "https://auth.opensky-network.org/auth/realms/opensky-network/protocol/openid-connect/token";
        String body = "grant_type=client_credentials&client_id=mightysarabor-api-client&client_secret=uwbSEMZFpuNqat9hankSUPJC2R5VKwwN";
        String json = client.post(url, body, "application/x-www-form-urlencoded").responseBody();
        String token = parser.parseToTree(json).get("access_token").asText();

        HeaderRecord header = new HeaderRecord("Authorization", "Bearer "+token);

        OpenSkyAPIService requester = new OpenSkyAPIService("application/x-www-form-urlencoded", client, url, body, parser);
        requester.getFlightInfo();
    }
}
