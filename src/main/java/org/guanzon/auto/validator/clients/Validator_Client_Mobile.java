/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.clients;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
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
        if (poEntity.getMobileID().isEmpty()){
            psMessage = "Mobile ID is not set.";
            return false;
        }

        if (poEntity.getClientID().isEmpty()){
            psMessage = "Client ID is not set.";
            return false;
        }

        if (poEntity.getMobileNo().isEmpty()){
            psMessage = "Contact number is not set.";
            return false;
        }
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
    
    
}
