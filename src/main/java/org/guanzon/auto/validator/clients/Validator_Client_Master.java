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
            String lsSQL = poEntity.getSQL();

            if (poEntity.getClientID().isEmpty()){
                psMessage = "Client ID is not set.";
                return false;
            }

    //        validate first name and last name if client type is customer
    //        0 Client
    //        1 Company
    //        2 Institutional *EXCLUDED* 
            if (poEntity.getClientTp() == null){
                psMessage = "Client type is not set.";
                return false;
            }

            if(poEntity.getClientTp().equals("0")){
                if (poEntity.getLastName() == null){
                    psMessage = "Customer last name is not set.";
                    return false;
                }

                if (poEntity.getFirstName() == null){
                    psMessage = "Customer first name is not set.";
                    return false;
                }

                if (poEntity.getGender() == null){
                    psMessage = "Gender is not set.";
                    return false;
                }

                if (poEntity.getCvilStat() == null){
                    psMessage = "Civil Status is not set.";
                    return false;
                }

                if (poEntity.getLastName().isEmpty()){
                    psMessage = "Customer last name is not set.";
                    return false;
                }

                if (poEntity.getFirstName().isEmpty()){
                    psMessage = "Customer first name is not set.";
                    return false;
                }

                if (poEntity.getGender().isEmpty()){
                    psMessage = "Gender is not set.";
                    return false;
                }

                if (poEntity.getCvilStat().isEmpty()){
                    psMessage = "Civil Status is not set.";
                    return false;
                }

                lsSQL = MiscUtil.addCondition(lsSQL, "a.sFrstName = " + SQLUtil.toSQL(poEntity.getFirstName())) +
                                                        " AND a.sLastName = " + SQLUtil.toSQL(poEntity.getLastName()) + 
                                                        " AND a.sBirthPlc = " + SQLUtil.toSQL(poEntity.getBirthPlc()) + 
                                                        " AND a.sClientID <> " + SQLUtil.toSQL(poEntity.getClientID()); 
                                                        //" AND a.dBirthDte = " + SQLUtil.toSQL(formattedDate));
                System.out.println(lsSQL);
                ResultSet loRS = poGRider.executeQuery(lsSQL);

                if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                            lsCompnyNm = loRS.getString("sCompnyNm");
                            lsClientID = loRS.getString("sClientID");
                    }
                    psMessage = "Existing Customer Record.\n\nClient ID: " + lsClientID + "\nName: " + lsCompnyNm.toUpperCase() ;
                    MiscUtil.close(loRS);        
                    return false;
                }
            } else {
                if (poEntity.getCompnyNm().isEmpty()){
                    psMessage = "Company Name cannot be Empty.";
                    return false;
                }

                lsSQL = MiscUtil.addCondition(lsSQL, "a.sCompnyNm = " + SQLUtil.toSQL(poEntity.getCompnyNm()) +
                                                        " AND a.sTaxIDNox = " + SQLUtil.toSQL(poEntity.getTaxIDNo()) + 
                                                        " AND a.sClientID <> " + SQLUtil.toSQL(poEntity.getClientID())); 

                ResultSet loRS = poGRider.executeQuery(lsSQL);

                if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                            lsCompnyNm = loRS.getString("sCompnyNm");
                            lsClientID = loRS.getString("sClientID");
                    }
                    psMessage = "Existing Customer Record.\n\nClient ID: " + lsClientID + "\nName: " + lsCompnyNm.toUpperCase();
                    MiscUtil.close(loRS);        
                    return false;
                }
            }
            
            lsCompnyNm= "";lsClientID = "";
            if(poEntity.getTaxIDNo() != null){
                lsSQL = MiscUtil.addCondition(lsSQL, "a.sTaxIDNox = " + SQLUtil.toSQL(poEntity.getTaxIDNo()) + 
                                                        " AND a.sClientID <> " + SQLUtil.toSQL(poEntity.getClientID())); 

                ResultSet loRS = poGRider.executeQuery(lsSQL);

                if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                            lsCompnyNm = loRS.getString("sCompnyNm");
                            lsClientID = loRS.getString("sClientID");
                    }
                    psMessage = "Existing Customer TIN ID Record.\n\nClient ID: "+ lsClientID + "\nName: " + lsCompnyNm.toUpperCase() + ".";
                    MiscUtil.close(loRS);        
                    return false;
                }
            }
            
            lsCompnyNm= "";lsClientID = "";
            if(poEntity.getLTOID() != null){
                lsSQL = MiscUtil.addCondition(lsSQL, "a.sLTOIDxxx = " + SQLUtil.toSQL(poEntity.getLTOID()) + 
                                                        " AND a.sClientID <> " + SQLUtil.toSQL(poEntity.getClientID())); 

                ResultSet loRS = poGRider.executeQuery(lsSQL);
                if (MiscUtil.RecordCount(loRS) > 0){
                        while(loRS.next()){
                            lsCompnyNm = loRS.getString("sCompnyNm");
                            lsClientID = loRS.getString("sClientID");
                        }
                        psMessage = "Existing Customer LTO ID Record.\n\nClient ID: "+ lsClientID + "\nName: " + lsCompnyNm.toUpperCase() + ".";
                        MiscUtil.close(loRS);
                        return false;
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
