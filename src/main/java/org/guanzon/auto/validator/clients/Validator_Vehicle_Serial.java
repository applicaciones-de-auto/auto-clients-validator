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
import org.guanzon.auto.model.clients.Model_Vehicle_Serial_Master;

/**
 *
 * @author Arsiela
 */
public class Validator_Vehicle_Serial implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Vehicle_Serial_Master poEntity;
    
    public Validator_Vehicle_Serial(Object foValue){
        poEntity = (Model_Vehicle_Serial_Master) foValue;
    }
    
    @Override
    public void setGRider(GRider foValue) {
        poGRider = foValue;
    }

    @Override
    public boolean isEntryOkay() {
        
        if(poEntity.getVhclID() == null){
            psMessage = "Vehicle ID cannot be Empty.";
            return false;
        } else {
            if(poEntity.getVhclID().isEmpty()){
                psMessage = "Vehicle ID cannot be Empty.";
                return false;
            }
        }

        if(poEntity.getMakeID() == null){
            psMessage = "Make cannot be Empty.";
            return false;
        } else {
            if(poEntity.getMakeID().trim().isEmpty()){
                psMessage = "Make cannot be Empty.";
                return false;
            }
        }

        if(poEntity.getModelID() == null){
            psMessage = "Model cannot be Empty.";
            return false;
        } else {
            if(poEntity.getModelID().trim().isEmpty()){
                psMessage = "Model cannot be Empty.";
                return false;
            }
        }

        if(poEntity.getColorID() == null){
            psMessage = "Color cannot be Empty.";
            return false;
        } else {
            if(poEntity.getColorID().trim().isEmpty()){
                psMessage = "Color cannot be Empty.";
                return false;
            }
        }

        if(poEntity.getTypeID() == null){
            psMessage = "Type cannot be Empty.";
            return false;
        } else {
            if(poEntity.getTypeID().trim().isEmpty()){
                psMessage = "Type cannot be Empty.";
                return false;
            }
        }

        if(poEntity.getTransMsn() == null){
            psMessage = "Transmission cannot be Empty.";
            return false;
        } else {
            if(poEntity.getTransMsn().trim().isEmpty()){
                psMessage = "Transmission cannot be Empty.";
                return false;
            }
        }

        if(poEntity.getYearModl() == null || poEntity.getYearModl() == 0){
            psMessage = "Year cannot be Empty.";
            return false;
        }

        if(poEntity.getEngineNo() == null){
            psMessage = "Engine No cannot be Empty.";
            return false;
        } else {
            if(poEntity.getEngineNo().trim().isEmpty() || poEntity.getEngineNo().replace(" ", "").length() < 3 ){
                psMessage = "Invalid Engine Number.";
                return false;
            }
        }

        if(poEntity.getFrameNo() == null){
            psMessage = "Frame No cannot be Empty.";
            return false;
        } else {
            if(poEntity.getFrameNo().trim().isEmpty() || poEntity.getFrameNo().replace(" ","").length() < 6 ){
                psMessage = "Frame Engine Number.";
                return false;
            }
        }
        
        if(poEntity.getCSNo() == null && poEntity.getPlateNo()== null){
            psMessage = "CS / Plate Number cannot be empty.";
            return false;
        } else {
            if(poEntity.getCSNo().trim().isEmpty() && poEntity.getPlateNo().trim().isEmpty()){
                psMessage = "CS / Plate Number cannot be empty.";
                return false;
            } 
        }
        
        try {
            String lsID = "";
            String lsDesc  = "";
            String lsSQL =    "  SELECT "                 
                            + "  sSerialID "             
                            + ", sCSNoxxxx "           
                            + " FROM vehicle_serial " ;  

            lsSQL = MiscUtil.addCondition(lsSQL, " sCSNoxxxx = " + SQLUtil.toSQL(poEntity.getCSNo()) 
                                                    + " AND sSerialID <> " + SQLUtil.toSQL(poEntity.getSerialID()) 
                                                    );
            System.out.println("EXISTING VEHICLE SERIAL CS NO CHECK: " + lsSQL);
            ResultSet loRS = poGRider.executeQuery(lsSQL);

            if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                        lsID = loRS.getString("sSerialID");
                        lsDesc = loRS.getString("sCSNoxxxx");
                    }
                    
                    MiscUtil.close(loRS);
                    
                    psMessage = "Existing Vehicle Serial CS No Record.\n\nSerial ID: " + lsID + "\nCS No: " + lsDesc.toUpperCase() ;
                    return false;
            }
            
            lsID = "";
            lsDesc  = "";
            lsSQL =    "  SELECT "                 
                    + "  sSerialID "             
                    + ", sEngineNo "           
                    + " FROM vehicle_serial " ;  

            lsSQL = MiscUtil.addCondition(lsSQL, " sEngineNo = " + SQLUtil.toSQL(poEntity.getEngineNo()) 
                                                    + " AND sSerialID <> " + SQLUtil.toSQL(poEntity.getSerialID()) 
                                                    );
            System.out.println("EXISTING VEHICLE SERIAL ENGINE NO CHECK: " + lsSQL);
            loRS = poGRider.executeQuery(lsSQL);

            if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                        lsID = loRS.getString("sSerialID");
                        lsDesc = loRS.getString("sEngineNo");
                    }
                    
                    MiscUtil.close(loRS);
                    
                    psMessage = "Existing Vehicle Serial Engine No Record.\n\nSerial ID: " + lsID + "\nEngine No: " + lsDesc.toUpperCase()   ;
                    return false;
            }
            
            lsID = "";
            lsDesc  = "";
            lsSQL =    "  SELECT "                 
                    + "  sSerialID "             
                    + ", sFrameNox "           
                    + " FROM vehicle_serial " ;  

            lsSQL = MiscUtil.addCondition(lsSQL, " sFrameNox = " + SQLUtil.toSQL(poEntity.getEngineNo()) 
                                                    + " AND sSerialID <> " + SQLUtil.toSQL(poEntity.getSerialID()) 
                                                    );
            System.out.println("EXISTING VEHICLE SERIAL FRAME NO CHECK: " + lsSQL);
            loRS = poGRider.executeQuery(lsSQL);

            if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                        lsID = loRS.getString("sSerialID");
                        lsDesc = loRS.getString("sFrameNox");
                    }
                    
                    MiscUtil.close(loRS);
                    
                    psMessage = "Existing Vehicle Serial Frame No Record.\n\nSerial ID: " + lsID + "\nFrame No: " + lsDesc.toUpperCase()   ;
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
