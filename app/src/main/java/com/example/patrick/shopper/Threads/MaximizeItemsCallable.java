package com.example.patrick.shopper.Threads;

import android.widget.LinearLayout;
import com.example.patrick.shopper.Utility.Summary;
import com.example.patrick.shopper.Utility.ZeroOneKnapsack;

/**
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
        System.out.println("The callable was started!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

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

        System.out.println("DORUN WAS STARTED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");



        ZeroOneKnapsack knapsack = new ZeroOneKnapsack(budget);
        knapsack.initKnapsack();
        System.out.println("Instantiate knwpsack!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        //Add the items to the knapsack
        for(String itemSummary : itemsInfoArray) {
            System.out.println("ITEM SUMMARY: " + itemSummary + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            double itemCost = Summary.extractCost(itemSummary);
            knapsack.addItem(itemSummary, itemCost);
        }
        System.out.println("ADDED ALL THE ITEMS TO THE KNAPSACK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");

        String answer = knapsack.solve();
        System.out.println("ANSWER: " + answer + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        System.out.println("DORUN IS ABOUT TO END!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return answer;


    }
}
