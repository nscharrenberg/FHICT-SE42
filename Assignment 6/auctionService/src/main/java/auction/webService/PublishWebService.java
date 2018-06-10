/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.webService;

import javax.xml.ws.Endpoint;

/**
 *
 * @author Noah Scharrenberg
 */
public class PublishWebService {
    private static final String url = "http://localhost:8181/auction";
    
    public static void main(String[] args) { 
        Endpoint.publish(url, new Auction());
    }
}
