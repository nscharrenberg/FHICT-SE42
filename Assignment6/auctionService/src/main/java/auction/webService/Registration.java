/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.webService;

import auction.domain.User;
import auction.service.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import util.DatabaseCleaner;

/**
 *
 * @author Noah Scharrenberg
 */
@WebService
public class Registration {
    private RegistrationMgr registrationMgr;
    
    

    public Registration() {
        this.registrationMgr = new RegistrationMgr();
        
    }
    
    public User registerUser(String email) {
        return registrationMgr.registerUser(email);
    }  
    
    public User getUser(String email) {
        return registrationMgr.getUser(email);
    }
    
    public List<User> getUsers() {
        return registrationMgr.getUsers();
    }
}
