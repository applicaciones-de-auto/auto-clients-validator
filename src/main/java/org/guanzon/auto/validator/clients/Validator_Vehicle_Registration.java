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
import org.guanzon.auto.model.clients.Model_Vehicle_Registration;

/**
 *
 * @author Arsiela
 */
public class Validator_Vehicle_Registration implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Vehicle_Registration poEntity;
    
    public Validator_Vehicle_Registration(Object foValue){
        poEntity = (Model_Vehicle_Registration) foValue;
    }
    
    @Override
    public void setGRider(GRider foValue) {
        poGRider = foValue;
    }

    @Override
    public boolean isEntryOkay() {
        try {
            if(poEntity.getPlateNo() != null){
               if(!poEntity.getPlateNo().trim().isEmpty()){
                    String lsID = "";
                    String lsDesc  = "";
                    String lsSQL =    "  SELECT "                 
                                    + "  sSerialID "             
                                    + ", sPlateNox "           
                                    + " FROM vehicle_serial_registration " ;  

                    lsSQL = MiscUtil.addCondition(lsSQL, " sPlateNox = " + SQLUtil.toSQL(poEntity.getPlateNo()) 
                                                            + " AND sSerialID <> " + SQLUtil.toSQL(poEntity.getSerialID()) 
                                                            );
                    System.out.println("EXISTING VEHICLE SERIAL PLATE NO CHECK: " + lsSQL);
                    ResultSet loRS = poGRider.executeQuery(lsSQL);

                    if (MiscUtil.RecordCount(loRS) > 0){
                            while(loRS.next()){
                                lsID = loRS.getString("sSerialID");
                                lsDesc = loRS.getString("sPlateNox");
                            }

                            MiscUtil.close(loRS);

                            psMessage = "Existing Vehicle Serial Plate No Record.\n\nSerial ID: " + lsID + "\nPlate No: " + lsDesc.toUpperCase()  ;
                            return false;
                    }
                
                }
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
