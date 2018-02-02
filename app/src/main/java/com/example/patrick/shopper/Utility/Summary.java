package com.example.patrick.shopper.Utility;

import android.widget.LinearLayout;

import com.example.patrick.shopper.CustomViews.ItemView;

/**
 * Can not send views between activities. This abstract class will provide utility to summarize
 * all the listed items in a string format that can be sent. Will also provide utility to
 * separate the summarized list into individual Strings which summarizes the data of a single item.
 * As well as provide functionality to extract a single attribute of an item given its summary.
 *
 * Created by patrick on 13/01/18.
 */

public abstract class Summary {

    public static String INFO_DELIMITER = ";"; //Separates name, price and quantity attributes
    public static String ITEM_DELIMITER = "#";

    public static final int ITEM_NAME_INDEX = 0;
    public static final int ITEM_COST_INDEX = 1;
    public static final int ITEM_QUANTITY_INDEX = 2;

    /**
     * Need to send the data of the items as a string to another activity.
     *
     * @param itemList LinearLayout that is the parent to the item views.
     * @return The items added to the list represented as a String.
     */
    public static String summarizeListAsString(LinearLayout itemList) {
        String summary = "";
        for(int itemIndex = 0; itemIndex < itemList.getChildCount(); itemIndex++) {
            ItemView item = (ItemView) itemList.getChildAt(itemIndex);
            summary += item.getName() + INFO_DELIMITER;
            summary += Double.toString(item.getSinglePrice()) + INFO_DELIMITER;
            summary += Integer.toString(item.getQuantity()) + INFO_DELIMITER;
            summary += ITEM_DELIMITER;
        }
        //System.out.println("Summary before regex" + summary);
        summary.replaceAll(ITEM_DELIMITER + "%", "");
        //System.out.println("Summary after regex" + summary);
        return summary;
    }

    /**
     * Separate the summarized list of items where each each element in the array is the data
     * summary for a singular item
     * @param summarizedList Summarized list constructed by the method summarizeListAsString
     * @return A string array where each element in the information of a single item.
     */
    public static String[] separateSummarizedList(String summarizedList) {

        String[] items = summarizedList.split(Summary.ITEM_DELIMITER);

        return items;
    }

    /**
     * Separate an entry from the summarized list and return the information as String[].
     * @param summarizedData An entry from the summary containing information of the item.
     * @return String array with the item info in the format [name, price, quantity]
     */

    public static String[] separateItemInformation(String summarizedData) {
        assert !(summarizedData.equals(null) && summarizedData.equals("")) : "The data entry is not valid.";

        String[] data = summarizedData.split(INFO_DELIMITER);
        return data;
    }

    /**
     * Directly get the information of the items in an String array from the LinearLayout
     * which holds all the items.
     * @param itemList
     * @return
     */
    public static String[] getItemInfoFromLayout(LinearLayout itemList) {
        return separateSummarizedList(summarizeListAsString(itemList));
    }

    /**
     * Get the name of the item from the summary of an item's attributes.
     * @param itemInformation
     * @return The name from the information provided.
     */
    public static String extractName(String itemInformation) {
        assert itemInformation.equals(null) : "itemInformation is null";

        String[] itemAtrributes = itemInformation.split(INFO_DELIMITER);
        String itemName = itemAtrributes[ITEM_NAME_INDEX];
        return itemName;
    }

    /**
     * Get the cost of the item from the summary of an item's attributes.
     * @param itemInformation
     * @return The price of the item.
     */
    public static double extractCost(String itemInformation) {
        System.out.println("Provided information: " + itemInformation);

        assert itemInformation.equals(null) : "itemInformation is null";

        String[] itemAtrributes = itemInformation.split(INFO_DELIMITER);
        double itemCost = Double.parseDouble(itemAtrributes[ITEM_COST_INDEX]);
        return itemCost;
    }

    /**
     * Get the quantity of the item from the summary of an item's attributes.
     * @param itemInformation
     * @return The quantity from the information provided.
     */
    public static int extractQuantity(String itemInformation) {
        assert itemInformation.equals(null) : "itemInformation is null";

        String[] itemAtrributes = itemInformation.split(INFO_DELIMITER);
        int itemQuantity = Integer.parseInt(itemAtrributes[ITEM_QUANTITY_INDEX]);
        return itemQuantity;
    }


}
