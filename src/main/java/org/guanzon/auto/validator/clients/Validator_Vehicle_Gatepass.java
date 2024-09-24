/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.clients;

import java.sql.SQLException;
import java.util.logging.Level;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.clients.Model_Vehicle_Gatepass;

/**
 *
 * @author Arsiela
 */
public class Validator_Vehicle_Gatepass implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Vehicle_Gatepass poEntity;
    
    public Validator_Vehicle_Gatepass(Object foValue){
        poEntity = (Model_Vehicle_Gatepass) foValue;
    }
    
    @Override
    public void setGRider(GRider foValue) {
        poGRider = foValue;
    }

    @Override
    public boolean isEntryOkay() {
        if(poEntity.getTransNo()== null) {
            psMessage = "Transaction No is not set.";
            return false;
        } else {
            if (poEntity.getTransNo().isEmpty()){
                psMessage = "Transaction No is not set.";
                return false;
            }
        }
        if(poEntity.getSourceCD()== null) {
            psMessage = "Source Code is not set.";
            return false;
        } else {
            if (poEntity.getSourceCD().isEmpty()){
                psMessage = "Source Code is not set.";
                return false;
            }
        }
        if(poEntity.getSourceNo()== null) {
            psMessage = "Source No is not set.";
            return false;
        } else {
            if (poEntity.getSourceNo().isEmpty()){
                psMessage = "Source No is not set.";
                return false;
            }
        }
        if(poEntity.getSourceGr()== null) {
            psMessage = "Source Group is not set.";
            return false;
        } else {
            if (poEntity.getSourceGr().isEmpty()){
                psMessage = "Source Group is not set.";
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
