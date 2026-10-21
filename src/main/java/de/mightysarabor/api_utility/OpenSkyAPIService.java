package de.mightysarabor.api_utility;

import de.mightysarabor.records.HeaderRecord;
import de.mightysarabor.records.ResponseRecord;

import java.io.IOException;


public class OpenSkyAPIService {

    private APIClient client;
    private HeaderRecord header;

    public OpenSkyAPIService(HeaderRecord header, APIClient client) {
        this.client = client;
        this.header = header;
    }


    public String getFlightInfo(String accessToken, String url) throws IOException, InterruptedException {

            ResponseRecord getResponse = client.get(url, header);
            //System.out.println(getResponse.responseBody());
            return getResponse.responseBody();
        }

}
