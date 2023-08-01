package org.example;

public class StringOfOnlyDigitsException extends NumberFormatException {
    public StringOfOnlyDigitsException() {
        super("Параметр должен содержать только цифры");
    }

}
