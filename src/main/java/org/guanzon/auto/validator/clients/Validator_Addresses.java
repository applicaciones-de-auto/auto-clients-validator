/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.clients;

import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.clients.Model_Addresses;

/**
 *
 * @author Arsiela
 */
public class Validator_Addresses implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Addresses poEntity;
    
    public Validator_Addresses(Object foValue){
        poEntity = (Model_Addresses) foValue;
    }

    @Override
    public void setGRider(GRider foValue) {
        poGRider = foValue;
    }
    @Override
    public boolean isEntryOkay() {
        if (poEntity.getBrgyID().isEmpty()){
            psMessage = "Barangay is not set.";
            return false;
        }
        
        if (poEntity.getTownID().isEmpty()){
            psMessage = "Town is not set.";
            return false;
        }
        
//        if (poEntity.getProvID().isEmpty()){
//            psMessage = "Province is not set.";
//            return false;
//        }        
        
        return true;
    }
    
    @Override
    public String getMessage() {
        return psMessage;
    }
}
