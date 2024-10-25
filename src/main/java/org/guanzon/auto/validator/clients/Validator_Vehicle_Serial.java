/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.clients;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
        
        if(poEntity.getColorID() == null){
            psMessage = "Color cannot be Empty.";
            return false;
        } else {
            if(poEntity.getColorID().trim().isEmpty()){
                psMessage = "Color cannot be Empty.";
                return false;
            }
        }
        
        if(poEntity.getYearModl() == null || poEntity.getYearModl() == 0){
            psMessage = "Year cannot be Empty.";
            return false;
        }
        
//        if(poEntity.getValue("dRegister") != null){
//            // Assuming getvalue("dRegister") returns a Date object
//            Date dateValue = (Date) poEntity.getValue("dRegister");
//
//            // Convert Date to LocalDate
//            LocalDate localDate = dateValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//            // Get the year
//            int year = localDate.getYear();
//            if(year != 1900){
//                if(poEntity.getYearModl() > year){
//                psMessage = "Invalid date registration.";
//                return false;
//                }
//            }
//        }
        
        
        if(poEntity.getCSNo() == null && poEntity.getPlateNo()== null){
            psMessage = "CS / Plate Number cannot be empty.";
            return false;
        } else {
            if(poEntity.getCSNo().trim().isEmpty() && poEntity.getPlateNo().trim().isEmpty()){
                psMessage = "CS / Plate Number cannot be empty.";
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

        if(poEntity.getEngineNo() == null){
            psMessage = "Engine No cannot be Empty.";
            return false;
        } else {
            if(poEntity.getEngineNo().trim().isEmpty() || poEntity.getEngineNo().replace(" ", "").length() < 3 ){
                psMessage = "Invalid Engine Number.";
                return false;
            }
        }
        
        if(poEntity.getEngineNo().equals(poEntity.getFrameNo())){
            psMessage = "Engine No and Frame No cannot be the same.";
            return false;
        }
        
        if(poEntity.getSoldStat()== null){
            psMessage = "Vehicle Status cannot be Empty.";
            return false;
        } else {
            if(poEntity.getSoldStat().trim().isEmpty()){
                psMessage = "Vehicle Status cannot be Empty.";
                return false;
            }
        }
        
        if(!poEntity.getSoldStat().equals("0")){
            if(poEntity.getVhclNew()== null){
                psMessage = "Vehicle Category cannot be Empty.";
                return false;
            } else {
                if(poEntity.getVhclNew().trim().isEmpty()){
                    psMessage = "Vehicle Category cannot be Empty.";
                    return false;
                }
            }
        }
        
//        if(poEntity.getVhclNew()== null){
//            psMessage = "Vehicle Type cannot be Empty.";
//            return false;
//        } else {
//            if(poEntity.getVhclNew().trim().isEmpty()){
//                psMessage = "Vehicle Type cannot be Empty.";
//                return false;
//            }
//        }
        
//        if(poEntity.getIsDemo()== null){
//            psMessage = "Vehicle Demo cannot be Empty.";
//            return false;
//        } else {
//            if(poEntity.getIsDemo().trim().isEmpty()){
//                psMessage = "Vehicle Demo cannot be Empty.";
//                return false;
//            }
//        }
        
        try {
            /*CHECK IF MAKE IS A AFFILIATED MAKE: 
             * Vehicle make is a affiliated make and found in xxxstandard_sets ; Engine and Frame must be validate
             */
            String lsSQL =   " SELECT "
                           + "  sDescript "
                           + " , sValuexxx "
                           + " FROM xxxstandard_sets ";
            lsSQL = MiscUtil.addCondition(lsSQL, " (sDescript = 'affiliated_make' OR sDescript = 'mainproduct') ");
            System.out.println("AFFILIATED MAKE CHECK: " + lsSQL);
            ResultSet loRS = poGRider.executeQuery(lsSQL);

            if (MiscUtil.RecordCount(loRS) == 0){
                psMessage = "Please notify System Administrator to config `affiliated_make` or `mainproduct`.";
                return false;
            }
            
            lsSQL =   " SELECT "
                    + "  sDescript "
                    + " , sValuexxx "
                    + " FROM xxxstandard_sets ";
            lsSQL = MiscUtil.addCondition(lsSQL, " (sDescript = 'affiliated_make' OR sDescript = 'mainproduct') "
                                                    + " AND sValuexxx = " + SQLUtil.toSQL(poEntity.getMakeDesc()) 
                                                    );
            System.out.println("AFFILIATED MAKE AND MAIN PRODUCT CHECK: " + lsSQL);
            loRS = poGRider.executeQuery(lsSQL);

            if (MiscUtil.RecordCount(loRS) > 0){
                //Check body type do not validate engine and frame when body type is Motorcycle or Truck
                lsSQL =   " SELECT "
                        + "  sModelIDx "
                        + " , sBodyType "
                        + " FROM vehicle_model ";
                lsSQL = MiscUtil.addCondition(lsSQL, " (sBodyType <> '4' AND sBodyType <> '5') "
                                                        + " AND sModelIDx = " + SQLUtil.toSQL(poEntity.getModelID()) 
                                                        );
                System.out.println("BODY TYPE CHECK: " + lsSQL);
                loRS = poGRider.executeQuery(lsSQL);
                if (MiscUtil.RecordCount(loRS) > 0){
                
                    if(poEntity.getFrameNo().trim().isEmpty() || poEntity.getFrameNo().replace(" ", "").length() < 6 ){
                        psMessage = "Invalid Frame Number.";
                        return false;
                    }        

                    if(poEntity.getEngineNo().trim().isEmpty() || poEntity.getEngineNo().replace(" ", "").length() < 3 ){
                        psMessage = "Invalid Engine Number.";
                        return false;
                    }

                    int lnLength = 0;
                    String lsFrameNo = poEntity.getFrameNo().substring(0, 3);
                    lsSQL =    "  SELECT "                        
                                + "  sMakeIDxx "                     
                                + ", nEntryNox "                     
                                + ", sFrmePtrn "                       
                                + "FROM vehicle_make_frame_pattern ";

                    lsSQL = MiscUtil.addCondition(lsSQL, " sMakeIDxx = " + SQLUtil.toSQL(poEntity.getMakeID()) 
                                                            + " AND sFrmePtrn = " + SQLUtil.toSQL(lsFrameNo) 
                                                            );
                    System.out.println("MAKE FRAME CHECK: " + lsSQL);
                    loRS = poGRider.executeQuery(lsSQL);

                    if (MiscUtil.RecordCount(loRS) == 0){
                        psMessage = "Frame Number does not exist in Make Frame Pattern.";
                        return false;
                    }

                    //CHECK 4th and 5th character


                    lnLength = 0;
                    lsSQL =    "  SELECT "                        
                                + "  sModelIDx "                     
                                + ", nEntryNox "                     
                                + ", sFrmePtrn "                    
                                + ", nFrmeLenx "                         
                                + "FROM vehicle_model_frame_pattern ";

                    lsFrameNo = poEntity.getFrameNo().substring(3, 6);
                    lsSQL = MiscUtil.addCondition(lsSQL, " sModelIDx = " + SQLUtil.toSQL(poEntity.getModelID()) 
                                                            + " AND sFrmePtrn = " + SQLUtil.toSQL(lsFrameNo) 
                                                            );
                    System.out.println("MODEL FRAME CHECK: " + lsSQL);
                    loRS = poGRider.executeQuery(lsSQL);

                    if (MiscUtil.RecordCount(loRS) > 0){
                        while(loRS.next()){
                            lnLength = loRS.getInt("nFrmeLenx");
                        }

                        MiscUtil.close(loRS);
                        if(lnLength != poEntity.getFrameNo().length()) {
                            psMessage = "Frame Number Length does not equal to Model Frame Pattern Length.";
                            return false;
                        }
                    } else {
                        psMessage = "Frame Number does not exist in Model Frame Pattern.";
                        return false;
                    }

                    lnLength = 0;
                    lsSQL =    "  SELECT "                          
                                    + "  sModelIDx "                       
                                    + ", nEntryNox "                       
                                    + ", sEngnPtrn "                       
                                    + ", nEngnLenx "                           
                                    + "FROM vehicle_model_engine_pattern ";

                    String lsEngNo = poEntity.getEngineNo().substring(0, 3);
                    lsSQL = MiscUtil.addCondition(lsSQL, " sModelIDx = " + SQLUtil.toSQL(poEntity.getModelID()) 
                                                            + " AND sEngnPtrn LIKE " + SQLUtil.toSQL(lsEngNo) 
                                                            );
                    System.out.println("ENGINE NO CHECK: " + lsSQL);
                    loRS = poGRider.executeQuery(lsSQL);

                    if (MiscUtil.RecordCount(loRS) > 0){
                            while(loRS.next()){
                                lnLength = loRS.getInt("nEngnLenx");
                            }

                            MiscUtil.close(loRS);
                            if(lnLength != poEntity.getEngineNo().length()) {
                                psMessage = "Engine Number Length does not equal to Model Engine Pattern Length.";
                                return false;
                            }
                    } else {
                        psMessage = "Engine Number does not exist in Model Engine Pattern.";
                        return false;
                    }
                }
            }
            
            String lsID = "";
            String lsDesc  = "";
            if(poEntity.getCSNo() != null){
                if(!poEntity.getCSNo().trim().isEmpty()){
                    lsSQL =    "  SELECT "                 
                                + "  sSerialID "             
                                + ", sCSNoxxxx "           
                                + " FROM vehicle_serial " ;  

                    lsSQL = MiscUtil.addCondition(lsSQL, " sCSNoxxxx = " + SQLUtil.toSQL(poEntity.getCSNo()) 
                                                            + " AND sSerialID <> " + SQLUtil.toSQL(poEntity.getSerialID()) 
                                                            );
                    System.out.println("EXISTING VEHICLE SERIAL CS NO CHECK: " + lsSQL);
                    loRS = poGRider.executeQuery(lsSQL);

                    if (MiscUtil.RecordCount(loRS) > 0){
                            while(loRS.next()){
                                lsID = loRS.getString("sSerialID");
                                lsDesc = loRS.getString("sCSNoxxxx");
                            }

                            MiscUtil.close(loRS);

                            psMessage = "Existing Vehicle Serial CS No Record.\n\nSerial ID: " + lsID + "\nCS No: " + lsDesc.toUpperCase() ;
                            return false;
                    }
                }
            }
            
            lsID = "";
            lsDesc  = "";
            if(poEntity.getPlateNo() != null){
                if(!poEntity.getPlateNo().trim().isEmpty()){
                    lsSQL =   " SELECT "                                                               
                            + "   a.sSerialID "                                                         
                            + " , b.sPlateNox "                                                          
                            + " FROM vehicle_serial a "                                                
                            + " LEFT JOIN vehicle_serial_registration b ON b.sSerialID = a.sSerialID " ;

                    lsSQL = MiscUtil.addCondition(lsSQL, " b.sPlateNox = " + SQLUtil.toSQL(poEntity.getPlateNo()) 
                                                            + " AND a.sSerialID <> " + SQLUtil.toSQL(poEntity.getSerialID()) 
                                                            );
                    System.out.println("EXISTING VEHICLE SERIAL PLATE NO CHECK: " + lsSQL);
                    loRS = poGRider.executeQuery(lsSQL);

                    if (MiscUtil.RecordCount(loRS) > 0){
                            while(loRS.next()){
                                lsID = loRS.getString("sSerialID");
                                lsDesc = loRS.getString("sPlateNox");
                            }

                            MiscUtil.close(loRS);

                            psMessage = "Existing Vehicle Serial Plate No Record.\n\nSerial ID: " + lsID + "\nPlate No: " + lsDesc.toUpperCase()   ;
                            return false;
                    }
                }
            }
            lsID = "";
            lsDesc  = "";
            lsSQL =    "  SELECT "                 
                    + "  sSerialID "             
                    + ", sFrameNox "           
                    + " FROM vehicle_serial " ;  

            lsSQL = MiscUtil.addCondition(lsSQL, " sFrameNox = " + SQLUtil.toSQL(poEntity.getFrameNo()) 
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
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Validator_Vehicle_Serial.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
    
}
