package de.mightysarabor;

import de.mightysarabor.api_utility.API_Client;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException {
        System.out.println( "Hello World!" );
        API_Client client = new API_Client();
        client.makeRequest("https://jsonplaceholder.typicode.com/users/1");
    }
}
