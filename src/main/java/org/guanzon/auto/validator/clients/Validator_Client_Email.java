/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.clients;

import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.clients.Model_Client_Email;

/**
 *
 * @author Arsiela
 */
public class Validator_Client_Email implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Client_Email poEntity;
    
    public Validator_Client_Email(Object foValue){
        poEntity = (Model_Client_Email) foValue;
    }
    
    @Override
    public void setGRider(GRider foValue) {
        poGRider = foValue;
    }

    @Override
    public boolean isEntryOkay() {
        if (poEntity.getClientID().isEmpty()){
            psMessage = "Client ID is not set.";
            return false;
        }
        
        if (poEntity.getEmailID().isEmpty()){
            psMessage = "Email ID is not set.";
            return false;
        }
        if (poEntity.getEmailAdd().isEmpty()){
            psMessage = "Email is not set.";
            return false;
        }
        
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
}
