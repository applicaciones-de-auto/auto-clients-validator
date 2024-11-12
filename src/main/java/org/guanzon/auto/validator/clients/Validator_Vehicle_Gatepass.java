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
import org.guanzon.appdriver.constant.TransactionStatus;
import org.guanzon.auto.model.clients.Model_Vehicle_Gatepass;
import org.guanzon.auto.model.service.Model_JobOrder_Master;

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
        
        try {
            String lsID = "";
            String lsSQL =  poEntity.makeSelectSQL();
            lsSQL = MiscUtil.addCondition(lsSQL, " sSourceCD = " + SQLUtil.toSQL(poEntity.getSourceCD()) 
                                                    + " AND sTransNox <> " + SQLUtil.toSQL(poEntity.getTransNo()) 
                                                    + " AND cTranStat <> " + SQLUtil.toSQL(TransactionStatus.STATE_CANCELLED)
                                                    );
            System.out.println("EXISTING VEHICLE GATEPASS CHECK: " + lsSQL);
            ResultSet loRS = poGRider.executeQuery(lsSQL);

            if (MiscUtil.RecordCount(loRS) > 0){
                while(loRS.next()){
                    lsID = loRS.getString("sTransNox");
                }

                MiscUtil.close(loRS);
                psMessage = "Found existing Vehicle Gatepass No."+lsID+".\n\nSaving aborted.";
                return false;
            }
            
            lsID = "";
            Model_JobOrder_Master loEntity = new Model_JobOrder_Master(poGRider);
            lsSQL =  loEntity.makeSelectSQL();
            lsSQL = MiscUtil.addCondition(lsSQL, " sSourceNo = " + SQLUtil.toSQL(poEntity.getSourceCD()) 
                                                    + " AND cTranStat = " + SQLUtil.toSQL(TransactionStatus.STATE_OPEN)
                                                    );
            System.out.println("PENDING JOB ORDER CHECK: " + lsSQL);
            loRS = poGRider.executeQuery(lsSQL);

            if (MiscUtil.RecordCount(loRS) > 0){
                while(loRS.next()){
                    lsID = loRS.getString("sTransNox");
                }

                MiscUtil.close(loRS);
                psMessage = "Found un-done Job Order with JO No."+lsID+".\n\nSaving aborted.";
                return false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Validator_Vehicle_Gatepass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
    
}
