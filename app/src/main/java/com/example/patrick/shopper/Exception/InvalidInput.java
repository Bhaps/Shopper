package com.example.patrick.shopper.Exception;

/**
 * Custom exception for when the user enters invalid inputs.
 *
 * Created by patrick on 10/02/18.
 */

public class InvalidInput extends Exception {
    public InvalidInput(String message) {
        super(message);
    }
}
