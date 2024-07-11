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
        try {
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
            String lsCompnyNm = "";
            String lsClientID = "";
            String lsSQL = "SELECT " +
                            "  a.sClientID " +
                            ", a.sCompnyNm " +
                            ", b.sMobileID " +
                            ", b.sMobileNo " +
                            "FROM client_master a " +
                            "LEFT JOIN client_mobile b ON b.sClientID = a.sClientID " ;
            lsSQL = MiscUtil.addCondition(lsSQL, "b.sMobileNo = " + SQLUtil.toSQL(poEntity.getMobileNo())) +
                                                    " AND b.sMobileID <> " + SQLUtil.toSQL(poEntity.getMobileID()) ;
            
            System.out.println("EXISTING CONTACT NUMBER CHECK: " + lsSQL);
            ResultSet loRS = poGRider.executeQuery(lsSQL);
            if (MiscUtil.RecordCount(loRS) > 0){
                while(loRS.next()){
                    lsCompnyNm = loRS.getString("sCompnyNm");
                    lsClientID = loRS.getString("sClientID");
                }
                psMessage = "Existing Contact Number with Customer Record.\n\nClient ID: " + lsClientID + "\nName: " + lsCompnyNm.toUpperCase() ;
                MiscUtil.close(loRS);
                return false;

            }
        } catch (SQLException ex) {
            Logger.getLogger(Validator_Client_Mobile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
    
    
}
