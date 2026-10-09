package de.mightysarabor;

import de.mightysarabor.api_utility.API_Client;
import de.mightysarabor.parser.JSONParser;
import de.mightysarabor.records.CreatePostRequest;
import de.mightysarabor.records.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) throws IOException, InterruptedException {
        API_Client client = new API_Client();
        JSONParser parser = new JSONParser();
        String url = "https://auth.opensky-network.org/auth/realms/opensky-network/protocol/openid-connect/token";
        String body = "grant_type=client_credentials&client_id=mightysarabor-api-client&client_secret=uwbSEMZFpuNqat9hankSUPJC2R5VKwwN";
        String json = client.post(url, body, "application/x-www-form-urlencoded");
        String token = parser.parseToTree(json).get("access_token").asText();

        Map<String, String> headers = Map.of("Authorization", "Bearer "+token);




        String response = client.get(
                "https://opensky-network.org/api/states/all", headers
        );

        //System.out.println(parser.parseToTree(response).toPrettyString());
        System.out.println(response);

        CreatePostRequest request =
                new CreatePostRequest(
                        "",
                        "Sending objects instead of manually building JSON",
                        1
                );

        System.out.println(client.post(url, body, "application/x-www-form-urlencoded"));

    }
}
