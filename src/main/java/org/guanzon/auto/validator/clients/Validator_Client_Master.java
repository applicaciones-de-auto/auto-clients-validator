/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.clients;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.guanzon.appdriver.base.CommonUtils;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.auto.model.clients.Model_Client_Master;

/**
 *
 * @author Arsiela
 */
public class Validator_Client_Master implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Client_Master poEntity;
    
    public Validator_Client_Master(Object foValue){
        poEntity = (Model_Client_Master) foValue;
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

    //        validate first name and last name if client type is customer
    //        0 Client
    //        1 Company
    //        2 Institutional *EXCLUDED* 
            
            if(poEntity.getClientTp() == null) {
                psMessage = "Client type is not set.";
                return false;
            } else {
                if (poEntity.getClientTp().trim().isEmpty()){
                    psMessage = "Client type is not set.";
                    return false;
                }
            }

            if(poEntity.getClientTp().equals("0")){
                
                if(poEntity.getLastName() == null) {
                    psMessage = "Last name is not set.";
                    return false;
                } else {
                    if (poEntity.getLastName().trim().isEmpty()){
                        psMessage = "Last name is not set.";
                        return false;
                    }
                }
                
                if(poEntity.getFirstName() == null) {
                    psMessage = "First name is not set.";
                    return false;
                } else {
                    if (poEntity.getFirstName().trim().isEmpty()){
                        psMessage = "First name is not set.";
                        return false;
                    }
                }
                
                if(poEntity.getGender() == null) {
                    psMessage = "Gender is not set.";
                    return false;
                } else {
                    if (poEntity.getGender().trim().isEmpty()){
                        psMessage = "Gender is not set.";
                        return false;
                    }
                }

                if(poEntity.getCvilStat() == null) {
                    psMessage = "Civil Status is not set.";
                    return false;
                } else {
                    if (poEntity.getCvilStat().trim().isEmpty()){
                        psMessage = "Civil Status is not set.";
                        return false;
                    }
                }

                String lsBdate = "1900-01-01";
                if (poEntity.getValue("dBirthDte") == null){
                    psMessage = "Invalid Birthdate.";
                    return false;
                } 
                
                Date date = (Date) poEntity.getValue("dBirthDte");
                System.out.println(date);
                String formattedDate = "1900-01-01";
                // Define the date format
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOld = null;
                try {
                    // Parse the formatted date string into a Date object
                    dateOld = sdf.parse(formattedDate);

                    // Output the Date object
                    System.out.println("Converted Date: " + date);

                } catch (ParseException e) {
                    System.err.println("Error parsing date: " + e.getMessage());
                }
                
                
                if(date == dateOld || String.valueOf(date).equals(String.valueOf(dateOld)) || String.valueOf(date).equals("Mon Jan 01 00:00:00 CST 1900")){
                    psMessage = "Invalid Birthdate.";
                    return false;
                } 
                
                
                lsSQL = poEntity.getSQL();
                lsSQL = MiscUtil.addCondition(lsSQL, "a.sFrstName = " + SQLUtil.toSQL(poEntity.getFirstName())) +
                                                        " AND a.sLastName = " + SQLUtil.toSQL(poEntity.getLastName()) +
                                                        " AND a.dBirthDte = " + SQLUtil.toSQL(date) +
                                                        " AND a.sClientID <> " + SQLUtil.toSQL(poEntity.getClientID()) ;
                System.out.println("EXISTING CUSTOMER WITH SAME FIRST|LAST NAME AND BIRTHDATE CHECK: " + lsSQL);
                ResultSet loRS = poGRider.executeQuery(lsSQL);

                if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                            lsCompnyNm = loRS.getString("sCompnyNm");
                            lsClientID = loRS.getString("sClientID");
                    }
                    psMessage = "Existing Record.\n\nID: " + lsClientID + "\nName: " + lsCompnyNm.toUpperCase() ;
                    MiscUtil.close(loRS);        
                    return false;
                }
            } else {
                //Validate Company Name
                if(poEntity.getCompnyNm() == null) {
                    psMessage = "Company Name is not set.";
                    return false;
                } else {
                    if (poEntity.getCompnyNm().trim().isEmpty()){
                        psMessage = "Company Name is not set.";
                        return false;
                    }
                }
                
                if(poEntity.getTaxIDNo() == null){
                    psMessage = "TIN ID cannot be Empty.";
                    return false;
                } else {
                    if(poEntity.getTaxIDNo().trim().isEmpty()){
                        psMessage = "TIN ID cannot be Empty.";
                        return false;
                    }
                }
                
                // REPLACE(CONCAT(IFNULL(a.sHouseNox,''), IFNULL(a.sAddressx,''),IFNULL(c.sBrgyName,''), IFNULL(b.sTownName,''), IFNULL(d.sProvName,'')), ' ', '') 
                lsSQL = poEntity.getSQL();
                lsSQL = MiscUtil.addCondition(lsSQL, "REPLACE(a.sCompnyNm, ' ','') = " + SQLUtil.toSQL(poEntity.getCompnyNm().replace(" ", "")) +
                                                        " AND a.sTaxIDNox = " + SQLUtil.toSQL(poEntity.getTaxIDNo()) + 
                                                        " AND a.sClientID <> " + SQLUtil.toSQL(poEntity.getClientID())); 
                System.out.println("EXISTING COMPANY WITH SAME TIN ID CHECK: " + lsSQL);
                ResultSet loRS = poGRider.executeQuery(lsSQL);

                if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                            lsCompnyNm = loRS.getString("sCompnyNm");
                            lsClientID = loRS.getString("sClientID");
                    }
                    psMessage = "Existing Record.\n\nID: " + lsClientID + "\nName: " + lsCompnyNm.toUpperCase();
                    MiscUtil.close(loRS);        
                    return false;
                }
            }
            
            //Validate TIN ID
            lsCompnyNm= "";lsClientID = "";
            lsSQL = poEntity.getSQL();
            if(poEntity.getTaxIDNo() != null){
                if(!poEntity.getTaxIDNo().isEmpty()){
                    lsSQL = MiscUtil.addCondition(lsSQL, "a.sTaxIDNox = " + SQLUtil.toSQL(poEntity.getTaxIDNo()) + 
                                                            " AND a.sClientID <> " + SQLUtil.toSQL(poEntity.getClientID())); 
                    System.out.println("EXISTING TIN ID CHECK: " + lsSQL);
                    ResultSet loRS = poGRider.executeQuery(lsSQL);

                    if (MiscUtil.RecordCount(loRS) > 0){
                        while(loRS.next()){
                                lsCompnyNm = loRS.getString("sCompnyNm");
                                lsClientID = loRS.getString("sClientID");
                        }
                        psMessage = "Existing TIN ID Record.\n\nID: "+ lsClientID + "\nName: " + lsCompnyNm.toUpperCase() + ".";
                        MiscUtil.close(loRS);        
                        return false;
                    }
                }
            }
            
            //Validated LTO ID
            lsCompnyNm= "";lsClientID = "";
            lsSQL = poEntity.getSQL();
            if(poEntity.getLTOID() != null){
                if(!poEntity.getLTOID().isEmpty()){
                    lsSQL = MiscUtil.addCondition(lsSQL, "a.sLTOIDxxx = " + SQLUtil.toSQL(poEntity.getLTOID()) + 
                                                            " AND a.sClientID <> " + SQLUtil.toSQL(poEntity.getClientID())); 
                    System.out.println("EXISTING LTO ID CHECK: " + lsSQL);
                    ResultSet loRS = poGRider.executeQuery(lsSQL);
                    if (MiscUtil.RecordCount(loRS) > 0){
                            while(loRS.next()){
                                lsCompnyNm = loRS.getString("sCompnyNm");
                                lsClientID = loRS.getString("sClientID");
                            }
                            psMessage = "Existing LTO ID Record.\n\nID: "+ lsClientID + "\nName: " + lsCompnyNm.toUpperCase() + ".";
                            MiscUtil.close(loRS);
                            return false;
                    }
                }
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(Validator_Client_Master.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
    
}
