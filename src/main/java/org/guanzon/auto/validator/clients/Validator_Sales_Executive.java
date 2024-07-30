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
import org.guanzon.auto.model.clients.Model_Sales_Executive;

/**
 *
 * @author Arsiela
 */
public class Validator_Sales_Executive implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Sales_Executive poEntity;
    
    public Validator_Sales_Executive(Object foValue){
        poEntity = (Model_Sales_Executive) foValue;
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
                
            lsSQL = poEntity.getSQL();
            lsSQL = MiscUtil.addCondition(lsSQL," a.sClientID = " + SQLUtil.toSQL(poEntity.getClientID())) ;
            System.out.println("EXISTING SALES EXECUTIVE: " + lsSQL);
            ResultSet loRS = poGRider.executeQuery(lsSQL);

            if (MiscUtil.RecordCount(loRS) > 0){
                while(loRS.next()){
                        lsCompnyNm = loRS.getString("sCompnyNm");
                        lsClientID = loRS.getString("sClientID");
                }
                psMessage = "Existing Sales Executive Record.\n\nEmployee ID: " + lsClientID + "\nName: " + lsCompnyNm.toUpperCase() ;
                MiscUtil.close(loRS);        
                return false;
            }
            
        
        } catch (SQLException ex) {
            Logger.getLogger(Validator_Sales_Executive.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
    
    
}
