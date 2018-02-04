package com.example.patrick.shopper.Threads;

import android.widget.LinearLayout;
import com.example.patrick.shopper.Utility.Summary;
import com.example.patrick.shopper.Utility.ZeroOneKnapsack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The Callable thread that prepares and starts the zero one knapsack algorithm to maximize the
 * list of items.
 *
 * Created by patrick on 19/01/18.
 */
public class MaximizeItemsCallable extends NotifyingCallable {
    private double budget;

    //Each element in the summary of an items attributes, can be used to recreate an ItemView.
    private String[] itemsInfoArray;


    /** Initialize the Callable by passing the needed budget and the LinearLayout which contains
     * all the items that were added.
     */
    public MaximizeItemsCallable(double budget, LinearLayout itemList) {
        this.budget = budget;
        itemsInfoArray = Summary.getItemInfoFromLayout(itemList);
    }

    /**
     * Start the ZeroOneKnapsack to maximize the number of items the user can buy while
     * staying under the specified budget.
     * @return Returns the summarized list of items that are part of the maximized group of items.
     */
    @Override
    public String doRun() throws Exception {
        ZeroOneKnapsack knapsack = new ZeroOneKnapsack(budget);
        knapsack.initKnapsack();

        //Add the items to the knapsack.
        for(String itemSummary : itemsInfoArray) {
            System.out.println("ITEM SUMMARY: " + itemSummary + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            double itemCost = Summary.extractCost(itemSummary);
            int itemQuantity = Summary.extractQuantity(itemSummary);

            //Add an item the same amount of times as specified by its quantity in the item
            // information. Will later tally how many items there were and produce
            //a final list where the new quantity of each item is displayed
            for(int i = 0; i < itemQuantity; i++){
                System.out.println("Added: " + itemSummary);
                knapsack.addItem(itemSummary, itemCost);
            }
        }

        String maximizedList = knapsack.solve();
        System.out.println("ANSWER: " + maximizedList + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        //Still need to tally the duplicate items
        String finalList = tallyItems(maximizedList);

        System.out.println("FINAL LIST: " + finalList);

        return finalList;
    }

    /**
     *
     * @param summarizedList
     * @return
     */
    private String tallyItems(String summarizedList) {

        System.out.println("The maximized list provided: " + summarizedList);

        String finalSummary = "";
        //Will use the item information as the key and the value will the quantity of that item
        //in the list
        HashMap<String, Integer> itemTally = new HashMap<>();

        String[] items = Summary.separateSummarizedList(summarizedList);
        for(String itemInfo : items) {
            if(itemTally.containsKey(itemInfo)) {
                //Increase the quantity by one
                itemTally.put(itemInfo, itemTally.get(itemInfo) + 1);
            } else {
                //The item isn't in the HashMap yet, add the itemInfo with a quantity of 1
                itemTally.put(itemInfo, 1);
            }
        }

        //Recreate the summary with the updated quantity for an item.
        //The key is the old item info summary
        Set<String> keys = itemTally.keySet();
        for(String key : keys) {
            System.out.println("Key: " + key);

            int newItemQuantity = itemTally.get(key);
            String itemName = Summary.extractName(key);
            double cost = Summary.extractCost(key);

            String newItemInfo = Summary.createItemInfo(itemName, cost, newItemQuantity);

            finalSummary += newItemInfo + Summary.ITEM_DELIMITER;
        }

        //Remove the last delimiter since it's not supposed to be there as there is no following
        //item information.
        finalSummary = finalSummary.replaceAll(Summary.ITEM_DELIMITER + "$", "");

        return finalSummary;

    }
}
