package de.mightysarabor.api_utility;

import de.mightysarabor.parser.JSONParser;
import de.mightysarabor.records.HeaderRecord;
import de.mightysarabor.records.ResponseRecord;

import java.io.IOException;


public class OpenSkyAPIService {

    private API_Client client;

    public OpenSkyAPIService(HeaderRecord header, API_Client client) {
        this.client = client;
    }


    public String getFlightInfo(String accessToken, String url) throws IOException, InterruptedException {

            HeaderRecord getHeader = new HeaderRecord("Authorization", "Bearer " + accessToken);
            ResponseRecord getResponse = client.get(url, getHeader);
            System.out.println(getResponse.responseBody());
            return getResponse.responseBody();
        }

}
