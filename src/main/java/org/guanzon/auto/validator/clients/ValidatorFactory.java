/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.clients;

/**
 *
 * @author Arsiela
 */
public class ValidatorFactory {

    public enum TYPE {
        Client_Master,
        Client_Address,
        Client_Mobile,
        Client_Email,
        Client_Social_Media,
        Addresses,
        Vehicle_Serial,
        Vehicle_Registration,
        Sales_Executive,
        Sales_Agent,
        Service_Mechanic,
        Service_Advisor,
        Vehicle_Gatepass,
        Vehicle_Gatepass_Released_Items
    }

    public static ValidatorInterface make(ValidatorFactory.TYPE foType, Object foValue) {
        switch (foType) {
            case Client_Master:
                return new Validator_Client_Master(foValue);
            case Client_Address:
                return new Validator_Client_Address(foValue);
            case Client_Mobile:
                return new Validator_Client_Mobile(foValue);
            case Client_Email:
                return new Validator_Client_Email(foValue);
            case Client_Social_Media:
                return new Validator_Client_Social_Media(foValue);
            case Addresses:
                return new Validator_Addresses(foValue);
            case Sales_Executive:
                return new Validator_Sales_Executive(foValue);
            case Sales_Agent:
                return new Validator_Sales_Agent(foValue);
            case Vehicle_Serial:
                return new Validator_Vehicle_Serial(foValue);
            case Vehicle_Registration:
                return new Validator_Vehicle_Registration(foValue);
            case Service_Mechanic:
                return new Validator_Service_Mechanic(foValue);
            case Service_Advisor:
                return new Validator_Service_Advisor(foValue);
            case Vehicle_Gatepass:
                return new Validator_Vehicle_Gatepass(foValue);
            case Vehicle_Gatepass_Released_Items:
                return new Validator_Vehicle_Gatepass_Released_Items(foValue);
            default:
                return null;
        }
    }
    
}
