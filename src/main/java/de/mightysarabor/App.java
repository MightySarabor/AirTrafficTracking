package de.mightysarabor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.mightysarabor.api_utility.AccessTokenService;
import de.mightysarabor.api_utility.OpenSkyAPIService;
import de.mightysarabor.api_utility.APIClient;
import de.mightysarabor.parser.JSONParser;
import de.mightysarabor.records.*;
import de.mightysarabor.util.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) throws IOException, InterruptedException {
        APIClient client = new APIClient();
        JSONParser parser = new JSONParser();
        ObjectMapper mapper = new ObjectMapper();
        final String getTokenUrl = "https://auth.opensky-network.org/auth/realms/opensky-network/protocol/openid-connect/token";
        final String getFlightInfoURL = "https://opensky-network.org/api/states/all?lamin=45.8389&lomin=5.9962&lamax=47.8229&lomax=10.5226";
        final String clientID = Config.getSecret("CLIENT_ID");
        final String clientSecret = Config.getSecret("CLIENT_SECRET");
        final String clientURL = "grant_type=client_credentials&client_id=" +clientID +"&client_secret=" + clientSecret;
        AccessTokenService accessTokenService = new AccessTokenService("application/x-www-form-urlencoded", client, getTokenUrl, clientURL, parser );
        HeaderRecord header = accessTokenService.getAuthorizationHeader();
        OpenSkyAPIService openSkyAPIService = new OpenSkyAPIService(header, client);

        long remaining;
        String token = header.value();

        System.out.println("Token: " + token);


        while (true) {
            remaining = accessTokenService.getRemainingTime(token);
            System.out.println("Noch gültig: " + remaining / 60 + " min " + remaining % 60 + " s");

            Thread.sleep(10000);
            if(accessTokenService.getRemainingTime(token) <= 1750) {
                header = accessTokenService.getAuthorizationHeader();
                token = header.value();

            }
            JsonNode flightInfoJson = parser.parseToTree(openSkyAPIService.getFlightInfo(token, getFlightInfoURL));

            for(JsonNode flight : flightInfoJson.get("states"))
                {
                    List<FlightInfo> flights = new ArrayList<>();
                    flights.add(new FlightInfo(flight.get(0).asText(), flight.get(1).asText(), flight.get(8).asInt()));
                }
                //Put Kafka Producer here


        }



    }
}
