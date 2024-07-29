/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.clients;

import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.clients.Model_Client_Address;

/**
 *
 * @author Arsiela
 */
public class Validator_Client_Address implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Client_Address poEntity;
    
    public Validator_Client_Address(Object foValue){
        poEntity = (Model_Client_Address) foValue;
    }

    @Override
    public void setGRider(GRider foValue) {
        poGRider = foValue;
    }
    @Override
    public boolean isEntryOkay() {
        
        if(poEntity.getClientID() == null) {
            psMessage = "Client ID is not set.";
            return false;
        } else {
            if (poEntity.getClientID().isEmpty()){
                psMessage = "Client ID is not set.";
                return false;
            }
        }
        
        if(poEntity.getBrgyID() == null) {
            psMessage = "Barangay is not set.";
            return false;
        } else {
            if (poEntity.getBrgyID().isEmpty()){
                psMessage = "Barangay is not set.";
                return false;
            }
        }
        
        if(poEntity.getTownID() == null) {
            psMessage = "Town is not set.";
            return false;
        } else {
            if (poEntity.getTownID().isEmpty()){
                psMessage = "Town is not set.";
                return false;
            }
        }
        
        return true;
    }
    

    @Override
    public String getMessage() {
        return psMessage;
    }
    
}
