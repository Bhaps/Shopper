package com.gmail.patrickma345.shopper.Exception;

/**
 * Custom exception for when the user enters invalid inputs.
 *
 * Created by patrickma345 on 10/02/18.
 */

public class InvalidInput extends Exception {
    public InvalidInput(String message) {
        super(message);
    }
}
