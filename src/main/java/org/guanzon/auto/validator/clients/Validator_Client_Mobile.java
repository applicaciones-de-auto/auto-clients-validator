/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.clients;

import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.clients.Model_Client_Mobile;

/**
 *
 * @author Arsiela
 */
public class Validator_Client_Mobile implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Client_Mobile poEntity;
    
    public Validator_Client_Mobile(Object foValue){
        poEntity = (Model_Client_Mobile) foValue;
    }
    
    @Override
    public void setGRider(GRider foValue) {
        poGRider = foValue;
    }

    @Override
    public boolean isEntryOkay() {
        
        if(poEntity.getMobileID() == null){
            psMessage = "Mobile ID cannot be Empty.";
            return false;
        } else {
            if(poEntity.getMobileID().trim().isEmpty()){
                psMessage = "Mobile ID cannot be Empty.";
                return false;
            }
        }
        
        if(poEntity.getClientID() == null){
            psMessage = "Client ID cannot be Empty.";
            return false;
        } else {
            if(poEntity.getClientID().trim().isEmpty()){
                psMessage = "Client ID cannot be Empty.";
                return false;
            }
        }
        
        if(poEntity.getMobileNo() == null){
            psMessage = "Contact number cannot be Empty.";
            return false;
        } else {
            if(poEntity.getMobileNo().trim().isEmpty()){
                psMessage = "Contact number cannot be Empty.";
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
