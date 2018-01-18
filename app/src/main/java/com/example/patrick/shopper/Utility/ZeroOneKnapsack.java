package com.example.patrick.shopper.Utility;

import android.os.health.SystemHealthManager;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Executes the zero one knapsack. Takes a list of items with
 *
 * Return the index.
 *
 * Created by patrick on 14/01/18.
 */

public class ZeroOneKnapsack {

    private ArrayList<Item> items;
    private double[][] board; //Used in the algorithm
    private Double weight; //The maximum weight that can be taken i.e. the budget in dollars
    public final static Double DEFAULT_VALUE = 1.0;
    private final Double BASIC_UNIT = 10.0; //10 cents

    private int numItems;
    private int maxCapacityUnits;

    /**
     *
     * @param weight The maximum weight that can be taken i.e. the budget in dollars
     */
    public ZeroOneKnapsack(Double weight) {
        this.weight = weight;
    }

    /**
     * Add a new item to the array to be used in the algorithm.
     * @param info The needed information for the item.
     * @param cost The cost of the item in dollars. Will be converted into cents.
     */
    public void addItem(String info, Double cost) {
        items.add(new Item(info, cost, DEFAULT_VALUE));
    }

    /**
     * Set up the board where the work will be done and the items ArrayList where the items are
     * stored.
     */
    private void initKnapsack() {
        items = new ArrayList<Item>();

        //Need a base case where there are no items added. The algorithm starts at itemIndex 1
        //and capacityIndex 1. Hence first row and column are 0's to represent no items added.
        addItem("Buffer to represent no items", -1.0);

        numItems = items.size();
        maxCapacityUnits = calcUnits(weight);

        board = new double[numItems + 1][maxCapacityUnits + 1];

        //System.out.println("Number of items: " + numItems);
        //System.out.println(Arrays.deepToString(board));
    }

    /**
     * Starts the algorithm with the items that are in the items ArrayList.
     */
    private void start() {
        for(int itemIndex = 1; itemIndex <= numItems; itemIndex++) {
            int capacityIndex = 1;
            while(capacityIndex <= maxCapacityUnits) {

                //Subtract the item's unit costs from the current capacity as capacityIndex
                //to see the situation where there is enough budget to add the current item
                //Subtract 1 from the itemIndex to see the situation where the current item
                //hasn't been added yet (deciding if we can add it based on a previous collection
                //of items).
                double ifItemAdded = get_value(itemIndex - 1,
                        capacityIndex - calcUnits(items.get(itemIndex).getCost())) + DEFAULT_VALUE;

                //The value at the current capacity if we did not add the item at itemIndex.
                double ifItemNotAdded = get_value(itemIndex - 1, capacityIndex);

                board[itemIndex][capacityIndex] = Math.max(ifItemNotAdded, ifItemAdded);

                capacityIndex++;
            }
        }

    }

    /**
     * Get the value for the requested item index and capacity index. If either index have gone
     * out of index, return negative infinity.
     * @param itemIndex
     * @param capacityIndex
     * @return
     */
    private double get_value(int itemIndex, int capacityIndex) {
        if(itemIndex > numItems || capacityIndex > maxCapacityUnits ) {
            throw new java.lang.Error("The index(es) are out of bounds.");
        }

        if(itemIndex < 0 || capacityIndex < 0) {
            return Double.NEGATIVE_INFINITY;
        } else {
            return board[itemIndex][capacityIndex];
        }
    }

    /**
     * Takes the weight or cost in context of the situation (e.g. dollars) and converts it into
     * the equivalent number of units.
     * @param amountDollars The amount in dollars that will be converted into units.
     * @return The equivalent amount of units.
     */
    public int calcUnits(Double amountDollars) {
        Double amountCents = amountDollars * 100.0;

        //System.out.println("Amount in cents: " + amountCents.toString());
        //System.out.println("Rounded: " + (Math.round(amountCents)));
        //System.out.println("Units: " + (Math.round(amountCents) / BASIC_UNIT));
        //System.out.println();

        //Will truncate the result.
        int units = (int) (Math.round(amountCents) / BASIC_UNIT);
        return units;
    }


    /**
     * Used for testing
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Started running");
        ZeroOneKnapsack knapsack = new ZeroOneKnapsack(1.00);
        //System.out.println(knapsack.calcUnits(5.00));
        //System.out.println(knapsack.calcUnits(5.54));
        //System.out.println(knapsack.calcUnits(5.56));

        //knapsack.initKnapsack();

        knapsack.initKnapsack();
        System.out.println("Finished initializing");

        knapsack.addItem("A", 0.2);

        System.out.println("Finished adding items.");

        System.out.println("Started algorithm");

        knapsack.start();



        knapsack.displayBoard();
        System.out.println("Done");
    }

    public void displayBoard() {
        System.out.println(Arrays.deepToString(board));
    }



    /**
     * Item object used for the zero one knapsack algorithm. Used to encapsulate the needed
     * information and its cost.
     */
    public class Item {

        private String information;
        private Double cost;
        private Double value;

        public Item(String information, Double cost, Double value) {
            this.information = information;
            this.cost = cost;
            this.value = value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public void setCost(Double cost) {
            this.cost = cost;
        }

        public void setInformation(String information) {
            this.information = information;
        }

        public Double getValue() {
            return value;
        }

        public Double getCost() {
            return cost;
        }

        public String getInformation() {
            return information;
        }
    }

}
