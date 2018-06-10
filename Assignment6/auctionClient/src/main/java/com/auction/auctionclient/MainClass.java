///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.auction.auctionclient;
//
///**
// *
// * @author Noah Scharrenberg
// */
//public class MainClass {
//    public static void main(String[] args) {
//        User user = registerUser("NoahMail@mail.com");
//        
//        System.out.println("User ID: " + user.getId());
//        System.out.println("User Email: " + user.getEmail());
//    }  
//    
//    public static User registerUser(String email) {
//        try { // Call Web Service Operation
//            auction.webservice.RegistrationService service = new auction.webservice.RegistrationService();
//            auction.webservice.Registration port = service.getRegistrationPort();
//            // TODO initialize WS operation arguments here
//            // TODO process result here
//            User result = port.registerUser(email);
//            System.out.println("Result = "+result);
//            
//            return result;
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//}
