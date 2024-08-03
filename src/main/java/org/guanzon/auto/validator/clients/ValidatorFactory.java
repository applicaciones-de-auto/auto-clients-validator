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
    
    public enum TYPE{
        Client_Master,
        Client_Address,
        Client_Mobile,
        Client_Email,
        Client_Social_Media,
        Addresses,
        Vehicle_Serial,
        Vehicle_Registration,
        Sales_Executive,
        Sales_Agent
    }
    
    public static ValidatorInterface make(ValidatorFactory.TYPE foType, Object foValue){
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
            default:
                return null;
        }
    }
    
}
