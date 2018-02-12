package com.example.patrick.shopper.Utility;

/**
 * Checks various types of input.
 *
 * Created by patrick on 10/02/18.
 * @author Patrick Ma
 */

public abstract class InputCheck {

    /**
     * Check if the value contains the blacklisted characters.
     * @return true if it contains the blacklisted characters, false otherwise.
     */
    public static boolean passesBlacklistedCharCheck(String value) {
        try {
            for (String blacklistedChar : Summary.BLACKLISTED_CHARS) {
                if (value.contains(blacklistedChar)) {
                    return false;
                }
            }
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Checks if the item name is valid.
     * @param nameAsString String to be checked
     * @return true if valid, false otherwise.
     */
    public static boolean name (String nameAsString) {
        if(nameAsString == null || nameAsString.equals("") || !passesBlacklistedCharCheck(nameAsString)) {
            return false;
        } else  {
            return true;
        }
    }

    /**
     * Check if the entered cost of the item is valid.
     * @param itemCostAsString The entered cost by the user.
     * @return true if valid, false otherwise.
     */
    public static boolean cost (String itemCostAsString) {
        try {
            double itemCost = Double.parseDouble(itemCostAsString);
            if (itemCost > 0.0) {
                return true;
            } else {
                return false; //Item can't be negative or cost 0 dollars
            }
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Check if the entered quantity of the item is valid
     * @param quantityAsString The quantity entered by the user.
     * @return true if valid, false otherwise.
     */
    public static boolean quantity (String quantityAsString) {
        try {
            int itemQuantity = Integer.parseInt(quantityAsString);
            if (itemQuantity < 1 ) {
                return false; //Can't have negative or 0 items for its quantity
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }
}
