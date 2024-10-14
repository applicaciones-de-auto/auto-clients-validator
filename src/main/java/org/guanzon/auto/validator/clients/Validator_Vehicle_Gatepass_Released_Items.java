/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.clients;

import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.clients.Model_Vehicle_Gatepass_Released_Items;

/**
 *
 * @author Arsiela
 */
public class Validator_Vehicle_Gatepass_Released_Items implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Vehicle_Gatepass_Released_Items poEntity;
    
    public Validator_Vehicle_Gatepass_Released_Items(Object foValue){
        poEntity = (Model_Vehicle_Gatepass_Released_Items) foValue;
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
        if(poEntity.getItemType() == null) {
            psMessage = "Item Type is not set.";
            return false;
        } else {
            if (poEntity.getItemType().isEmpty()){
                psMessage = "Item Type is not set.";
                return false;
            }
        }
        
        if(poEntity.getItemCode()== null) {
            psMessage = "Item Code is not set."; 
            return false;
        } else {
            if (poEntity.getItemCode().isEmpty()){
                psMessage = "Item Code is not set.";
                return false;
            }
        }
//        switch(poEntity.getItemType()){
//            case "l":
//                if(poEntity.getLaborCde()== null) {
//                    psMessage = "Labor is not set."; 
//                    return false;
//                } else {
//                    if (poEntity.getLaborCde().isEmpty()){
//                        psMessage = "Labor is not set.";
//                        return false;
//                    }
//                }
//            break;
//            case "p":
//                if(poEntity.getStockID()== null) {
//                    psMessage = "Stock is not set.";
//                    return false;
//                } else {
//                    if (poEntity.getStockID().isEmpty()){
//                        psMessage = "Stock is not set.";
//                        return false;
//                    }
//                }
//            break;
//            case "d":
//            break;
//        
//        }
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
    
}
