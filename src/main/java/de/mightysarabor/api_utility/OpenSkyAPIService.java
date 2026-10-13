package de.mightysarabor.api_utility;

import de.mightysarabor.parser.JSONParser;
import de.mightysarabor.records.HeaderRecord;
import de.mightysarabor.records.ResponseRecord;

import java.io.IOException;


public class OpenSkyAPIService {

    private API_Client client;

    public OpenSkyAPIService(String header, API_Client Client, String url, String body, JSONParser parser) {
        this.client = Client;
    }


    public void getFlightInfo(String accessToken) throws IOException, InterruptedException {

            HeaderRecord getHeader = new HeaderRecord("Authorization", "Bearer " + accessToken);
            ResponseRecord getResponse = client.get("https://opensky-network.org/api/states/all?_limit=1", getHeader);
            System.out.println(getResponse.responseBody().substring(0, Math.min(20, getResponse.responseBody().length())));
        }

}
