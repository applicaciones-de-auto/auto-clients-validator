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
        
        if(poEntity.getClientID() == null) {
            psMessage = "Client ID is not set.";
            return false;
        } else {
            if (poEntity.getClientID().isEmpty()){
                psMessage = "Client ID is not set.";
                return false;
            }
        }
        
        if(poEntity.getEmailID() == null) {
            psMessage = "Email ID is not set.";
            return false;
        } else {
            if (poEntity.getEmailID().trim().isEmpty()){
                psMessage = "Email ID is not set.";
                return false;
            }
        }
        
        if(poEntity.getEmailAdd() == null) {
            psMessage = "Email is not set.";
            return false;
        } else {
            if (poEntity.getEmailAdd().trim().isEmpty()){
                psMessage = "Email is not set.";
                return false;
            }
        }
        
        //VALIDATE : Client Email
        try {
            String lsCompnyNm = "";
            String lsClientID = "";
            String lsSQL = "SELECT " +
                            "  a.sClientID " +
                            ", a.sCompnyNm " +
                            ", a.cClientTp " +
                            ", b.sEmailIDx " +
                            ", b.sEmailAdd " +
                            "FROM client_master a " +
                            "LEFT JOIN client_email_address b ON b.sClientID = a.sClientID " ;

            lsSQL = MiscUtil.addCondition(lsSQL, "b.sEmailAdd = " + SQLUtil.toSQL(poEntity.getEmailAdd())) +
                                                        " AND b.sEmailIDx <> " + SQLUtil.toSQL(poEntity.getEmailID()) ;

            System.out.println("EXISTING EMAIL ADDRESS CHECK: " + lsSQL);
            ResultSet loRS = poGRider.executeQuery(lsSQL);
            if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                        lsCompnyNm = loRS.getString("sCompnyNm");
                        lsClientID = loRS.getString("sClientID");
                    }

                    MiscUtil.close(loRS);
                    psMessage = "Existing Email Address with Customer Record.\n\nClient ID: " + lsClientID + "\nName: " + lsCompnyNm.toUpperCase();
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Validator_Client_Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
}
