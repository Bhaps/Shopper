package com.gmail.patrickma345.shopper.Threads;

import android.widget.LinearLayout;
import com.gmail.patrickma345.shopper.Utility.Summary;
import com.gmail.patrickma345.shopper.Utility.ZeroOneKnapsack;

/**
 * The Callable thread that prepares and starts the zero one knapsack algorithm to maximize the
 * list of items.
 *
 * Created by patrickma345 on 19/01/18.
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
        String maximizedListSummaries = knapsack.solve(itemsInfoArray);
        return maximizedListSummaries;
    }


}
