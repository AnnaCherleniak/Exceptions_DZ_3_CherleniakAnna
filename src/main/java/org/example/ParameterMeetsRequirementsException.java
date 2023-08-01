package org.example;

public class ParameterMeetsRequirementsException extends Exception{
    public ParameterMeetsRequirementsException(){
        super("Введенные данные не соответствуют трубованиям к параметру");
    }
}
