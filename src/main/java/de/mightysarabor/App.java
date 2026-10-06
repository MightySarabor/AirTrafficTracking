package de.mightysarabor;

import de.mightysarabor.api_utility.API_Client;
import de.mightysarabor.parser.JSONParser;
import de.mightysarabor.records.User;

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

        String response = client.get(
                "https://opensky-network.org/api/states/all"
        );

        System.out.println(parser.parseToTree(response).toPrettyString());
    }
}
