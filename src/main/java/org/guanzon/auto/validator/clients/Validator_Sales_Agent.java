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
import org.guanzon.auto.model.clients.Model_Sales_Agent;

/**
 *
 * @author Arsiela
 */
public class Validator_Sales_Agent implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Sales_Agent poEntity;
    
    public Validator_Sales_Agent(Object foValue){
        poEntity = (Model_Sales_Agent) foValue;
    }
    
    @Override
    public void setGRider(GRider foValue) {
        poGRider = foValue;
    }

    @Override
    public boolean isEntryOkay() {
        try {
            String lsCompnyNm = "";
            String lsClientID = "";
            String lsSQL = "";

            if(poEntity.getClientID() == null) {
                psMessage = "Client ID is not set.";
                return false;
            } else {
                if (poEntity.getClientID().isEmpty()){
                    psMessage = "Client ID is not set.";
                    return false;
                }
            }

            if(poEntity.getAgentTyp() == null) {
                psMessage = "Agent type is not set.";
                return false;
            } else {
                if (poEntity.getAgentTyp().isEmpty()){
                    psMessage = "Agent type is not set.";
                    return false;
                }
            }
                
            lsSQL = poEntity.getSQL();
            lsSQL = MiscUtil.addCondition(lsSQL," a.sClientID = " + SQLUtil.toSQL(poEntity.getClientID())) ;
            System.out.println("EXISTING REFERRAL AGENT: " + lsSQL);
            ResultSet loRS = poGRider.executeQuery(lsSQL);

            if (MiscUtil.RecordCount(loRS) > 0){
                while(loRS.next()){
                    lsCompnyNm = loRS.getString("sCompnyNm");
                    lsClientID = loRS.getString("sClientID");
                }
                psMessage = "Existing Referral Agent Record.\n\nReferral ID: " + lsClientID + "\nName: " + lsCompnyNm.toUpperCase() ;
                MiscUtil.close(loRS);        
                return false;
            }
            
        
        } catch (SQLException ex) {
            Logger.getLogger(Validator_Sales_Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
    
    
}
