/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.service;

import auction.webservice.Bid;
import auction.webservice.Category;
import auction.webservice.Item;
import auction.webservice.Money;
import auction.webservice.User;
import java.util.List;

/**
 *
 * @author Noah Scharrenberg
 */
public class WebLogic {
    public static User registerUser(String email) {
        auction.webservice.RegistrationService service = new auction.webservice.RegistrationService();
            auction.webservice.Registration port = service.getRegistrationPort();

            // TODO process result here
            auction.webservice.User result = port.registerUser(email);
            System.out.println("Result = "+result);
            
            return result;
    }
    
    public static Category addCategory(String description) {
        
        try { // Call Web Service Operation
            auction.webservice.AuctionService service = new auction.webservice.AuctionService();
            auction.webservice.Auction port = service.getAuctionPort();
            
            // TODO process result here
            auction.webservice.Category result = port.addCategory(description);
            System.out.println("Result = "+result);
            
            return result;
        } catch (Exception ex) {
           return null;
        }
    }
    
    public static Item offerItem(User seller, Category cat, String description) {
        try { // Call Web Service Operation
            auction.webservice.AuctionService service = new auction.webservice.AuctionService();
            auction.webservice.Auction port = service.getAuctionPort();
            
            // TODO process result here
            auction.webservice.Item result = port.offerItem(seller, cat, description);
            System.out.println("Result = "+result);
            
            return result;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static Item getItem(Long id) {
       
        try { // Call Web Service Operation
            auction.webservice.AuctionService service = new auction.webservice.AuctionService();
            auction.webservice.Auction port = service.getAuctionPort();

            // TODO process result here
            auction.webservice.Item result = port.getItem(id);
            System.out.println("Result = "+result);
            
            return result;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static List<Item> findItemByDescription(String description) {
        try { // Call Web Service Operation
            auction.webservice.AuctionService service = new auction.webservice.AuctionService();
            auction.webservice.Auction port = service.getAuctionPort();
            // TODO process result here
            java.util.List<auction.webservice.Item> result = port.findItemByDescription(description);
            System.out.println("Result = "+result);
            
            return result;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static Money addMoney(long cents, String currency) {
        
        try { // Call Web Service Operation
            auction.webservice.AuctionService service = new auction.webservice.AuctionService();
            auction.webservice.Auction port = service.getAuctionPort();
            // TODO process result here
            auction.webservice.Money result = port.addMoney(cents, currency);
            System.out.println("Result = "+result);
            
            return result;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static Bid newBid(Item item, User buyer, Money amount) {
        try { // Call Web Service Operation
            auction.webservice.AuctionService service = new auction.webservice.AuctionService();
            auction.webservice.Auction port = service.getAuctionPort();

            // TODO process result here
            auction.webservice.Bid result = port.newBid(item, buyer, amount);
            System.out.println("Result = "+result);
            
            return result;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static User getUser(String email) {
        
        try { // Call Web Service Operation
            auction.webservice.RegistrationService service = new auction.webservice.RegistrationService();
            auction.webservice.Registration port = service.getRegistrationPort();

            // TODO process result here
            auction.webservice.User result = port.getUser(email);
            System.out.println("Result = "+result);
            
            return result;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static List<User> getUsers() {
        try { // Call Web Service Operation
            auction.webservice.RegistrationService service = new auction.webservice.RegistrationService();
            auction.webservice.Registration port = service.getRegistrationPort();
            // TODO process result here
            java.util.List<auction.webservice.User> result = port.getUsers();
            System.out.println("Result = "+result);
            
            return result;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static boolean revokeItem(Item item) {
        try { // Call Web Service Operation
            auction.webservice.AuctionService service = new auction.webservice.AuctionService();
            auction.webservice.Auction port = service.getAuctionPort();
            // TODO process result here
            boolean result = port.revokeItem(item);
            System.out.println("Result = "+result);
            
            return result;
        } catch (Exception ex) {
            return false;
        }
    }
}
