package com.example.patrick.shopper.Threads;

import android.widget.LinearLayout;
import com.example.patrick.shopper.Utility.Summary;
import com.example.patrick.shopper.Utility.ZeroOneKnapsack;

import java.util.ArrayList;
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

        /*
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

                //Extract the cost from the itemSummary and set the knapsack item to that cost.
                //The quantity in the itemSummary will be untouched (despite the amount of this item
                //in the final answer may be different to its quantity as specified in its summary).
                //This will be changed later. the ZeroOneKnapsack.Item info attribute will
                //keep the original item summary.
                knapsack.addItem(itemSummary, itemCost);
            }
        }*/

        String maximizedListSummaries = knapsack.solve(itemsInfoArray);
        System.out.println("ANSWER: " + maximizedListSummaries + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");



        return maximizedListSummaries;
    }


}
