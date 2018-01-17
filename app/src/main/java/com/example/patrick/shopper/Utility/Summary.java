package com.example.patrick.shopper.Utility;

import android.widget.LinearLayout;

import com.example.patrick.shopper.CustomViews.ItemView;

/**
 * Can not send views between activities. This abstract class will provide utility to summarize
 * all the listed items in a string format that can be sent. Will also provide utility to
 * return an ItemView when given an entry from the summary.
 *
 * Created by patrick on 13/01/18.
 */

public abstract class Summary {

    public static String INFO_DELIMITER = ";"; //Separates name, price and quantity attributes
    public static String ITEM_DELIMITER = "\n";

    /**
     * Need to send the data of the items as a string to another activity.
     *
     * @param itemList LinearLayout that is the parent to the item views.
     * @return The items added to the list represented as a String.
     */
    public static String summarizeList(LinearLayout itemList) {
        String summary = "";
        for(int itemIndex = 0; itemIndex < itemList.getChildCount(); itemIndex++) {
            ItemView item = (ItemView) itemList.getChildAt(itemIndex);
            summary += item.getName() + INFO_DELIMITER;
            summary += Double.toString(item.getSinglePrice()) + INFO_DELIMITER;
            summary += Integer.toString(item.getQuantity()) + INFO_DELIMITER;
            summary += ITEM_DELIMITER;
        }
        return summary;
    }


    /**
     * Decode an entry from the summarized list and return an ItemView with the information.
     * @param summarizedData An entry from the summary containing information of the item.
     * @return String array with the item info in the format [name, price, quantity]
     */

    public static String[] retrieveItemInfo(String summarizedData) {
        assert !(summarizedData.equals(null) && summarizedData.equals("")) : "The data entry is not valid.";

        String[] data = summarizedData.split(INFO_DELIMITER);
        return data;
    }
}
