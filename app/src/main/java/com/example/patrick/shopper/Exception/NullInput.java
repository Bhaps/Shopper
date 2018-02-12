package com.example.patrick.shopper.Exception;

/**
 * This exception is thrown when a value is unexpected to be  null.
 *
 * Created by patrick on 11/02/18.
 */

public class NullInput extends Exception {
    public NullInput(String message) {
        super(message);
    }
}
