package de.mightysarabor.api_utility;

import de.mightysarabor.parser.JSONParser;
import de.mightysarabor.records.HeaderRecord;
import de.mightysarabor.records.ResponseRecord;

import java.io.IOException;

public class OpenSkyAPIService {

    private API_Client client;
    private String header;
    private String body;
    private String url;
    private JSONParser parser;


    public OpenSkyAPIService(String header, API_Client Client, String url, String body, JSONParser parser)
    {
        this.header = header;
        this.client = Client;
        this.body = body;
        this.url = url;
        this.parser = parser;

    }
    public void getFlightInfo() throws IOException, InterruptedException {

        ResponseRecord postResponse = client.post(url, body, header);
        System.out.println(postResponse.responseBody());

        String accessToken = parser.parseToTree(postResponse.responseBody()).get("access_token").asText();

        HeaderRecord getHeader = new HeaderRecord("Authorization", "Bearer "+accessToken);
        ResponseRecord getResponse = client.get("https://opensky-network.org/api/states/all?_limit=1", getHeader);
        System.out.println(getResponse.responseHeader());
    }
}
