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
import org.guanzon.auto.model.clients.Model_Client_Social_Media;

/**
 *
 * @author Arsiela
 */
public class Validator_Client_Social_Media implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Client_Social_Media poEntity;
    
    public Validator_Client_Social_Media(Object foValue){
        poEntity = (Model_Client_Social_Media) foValue;
    }
    
    @Override
    public void setGRider(GRider foValue) {
        poGRider = foValue;
    }

    @Override
    public boolean isEntryOkay() {
        if (poEntity.getSocialID().isEmpty()){
            psMessage = "Social ID is not set.";
            return false;
        }
        
        if (poEntity.getClientID().isEmpty()){
            psMessage = "Client ID is not set.";
            return false;
        }
        
        if (poEntity.getAccount().isEmpty()){
            psMessage = "Social account is not set.";
            return false;
        }
        
        //VALIDATE : Client Social Media
        try {
            String lsCompnyNm = "";
            String lsClientID = "";
            String lsSQL = "SELECT " +
                            "  a.sClientID " +
                            ", a.sCompnyNm " +
                            ", a.cClientTp " +
                            ", b.sSocialID " +
                            ", b.sAccountx " +
                            "FROM client_master a " +
                            "LEFT JOIN client_social_media b ON b.sClientID = a.sClientID " ;

            lsSQL = MiscUtil.addCondition(lsSQL, "b.sAccountx = " + SQLUtil.toSQL(poEntity.getAccount())) +
                                                        " AND b.sSocialID <> " + SQLUtil.toSQL(poEntity.getSocialID()) ;

            System.out.println("EXISTING SOCIAL MEDIA ACCOUNT CHECK: " + lsSQL);
            ResultSet loRS = poGRider.executeQuery(lsSQL);
            if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                        lsCompnyNm = loRS.getString("sCompnyNm");
                        lsClientID = loRS.getString("sClientID");
                    }

                    MiscUtil.close(loRS);
                    psMessage = "Existing Social Media Account with Customer Record.\n\nClient ID: " + lsClientID + "\nName: " + lsCompnyNm.toUpperCase();
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Validator_Client_Social_Media.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
}
