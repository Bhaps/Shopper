package com.example.patrick.shopper.Utility;

import java.util.ArrayList;
import java.util.Collections;

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
    private Double budget; //The maximum weight that can be taken i.e. the budget in dollars
    public final static Double DEFAULT_VALUE = 1.0;
    private final Double BASIC_UNIT = 10.0; //10 cents

    private int numItems;
    private int maxCapacityUnits;

    /**
     *
     * @param budget The maximum weight that can be taken i.e. the budget in dollars
     */
    public ZeroOneKnapsack(Double budget) {
        this.budget = budget;
    }

    /**
     * Add a new item to the array to be used in the algorithm.
     * @param info The needed information for the item.
     * @param cost The cost of the item in dollars. Will be converted into cents.
     */
    public void addItem(String info, Double cost) {

        //Round the cost of the item up, if rounded down will lose some of the item's cost
        //that may stack up to something significant and the final cost of hte
        //maximized list will be greater than the budget
        int costUnits = calcUnits(cost, true);

        items.add(new Item(info, cost, costUnits, DEFAULT_VALUE));
    }

    /**
     * Set up the board where the work will be done and the items ArrayList where the items are
     * stored.
     */
    public void initKnapsack() {
        items = new ArrayList<Item>();

        //Need a base case where there are no items added. The algorithm starts at itemIndex 1
        //and capacityIndex 1. Hence first row and column are 0's to represent no items added.
        addItem("Buffer to represent no items", -1.0);



        //System.out.println("Number of items: " + numItems);
        //System.out.println(Arrays.deepToString(board));
    }

    /**
     * Starts the algorithm with the items that are in the items ArrayList.
     */
    private void startKnapsack() {
        numItems = items.size();

        //Round down the budget, if we round up we might end up saying we have more money than
        //our budget actually is.
        maxCapacityUnits = calcUnits(budget, false);

        board = new double[numItems + 1][maxCapacityUnits + 1];

        for(int itemIndex = 1; itemIndex < numItems; itemIndex++) {
            int capacityIndex = 1;
            while(capacityIndex <= maxCapacityUnits) {

                //Subtract the item's unit costs from the current capacity as capacityIndex
                //to see the situation where there is enough budget to add the current item
                //Subtract 1 from the itemIndex to see the situation where the current item
                //hasn't been added yet (deciding if we can add it based on a previous collection
                //of items).
                double ifItemAdded = getValue(itemIndex - 1,
                        capacityIndex - items.get(itemIndex).getCostUnits()) + DEFAULT_VALUE;

                //The value at the current capacity if we did not add the item at itemIndex.
                double ifItemNotAdded = getValue(itemIndex - 1, capacityIndex);

                board[itemIndex][capacityIndex] = Math.max(ifItemNotAdded, ifItemAdded);

                capacityIndex++;
            }
        }

    }

    /**
     * Returns the indexes of the items that are part of the solution to the zero one knapsack
     * i.e. the maximized group of items that fits into the budget.
     * @return ArrayList of the item's indexes from items.
     */
    private ArrayList<Integer> getSolutionIndexes() {
        //Will contain the indexes of the items, from the array items, that are part of the solution
        ArrayList<Integer> addedItemIndexes = new ArrayList<>();

        //Start at the bottom right corner
        int itemIndex = numItems - 1; //There is the item acting as a buffer that represents
        //the situation of no items being added yet in the algorithm. It offets the indexes by one.
        int capacityIndex = maxCapacityUnits;

        while(itemIndex > 0 && capacityIndex > 0) {
            if(getValue(itemIndex, capacityIndex) != getValue(itemIndex - 1, capacityIndex)) {
                //Item was added to the knapsack
                addedItemIndexes.add(itemIndex);

                capacityIndex -= items.get(itemIndex).getCostUnits();
            }
            itemIndex -= 1; //Move down to the next item to see if it was added
        }

        return addedItemIndexes;
    }

    /**
     * Take an array of indexes and return the items they correspond to.
     * @param indexes The indexes from the elements in the ArrayList items.
     * @return An ArrayList of the items that are the solution to the knapsack.
     */
    private ArrayList<Item> getSolutionItems(ArrayList<Integer> indexes) {
        ArrayList<Item> solution = new ArrayList<>();

        for(int index : indexes) {
            solution.add(items.get(index));
        }

        return solution;
    }

    /**
     * Returns all the information of the items that are part of the solution.
     * @return String of all the information of the items.
     */
    public String solve() {
        startKnapsack();

        ArrayList<Integer> solutionIndexes = getSolutionIndexes();
        ArrayList<Item> solutionItems = getSolutionItems(solutionIndexes);

        //Retrieving the solution works backwards starting from the last item added
        //reverse the list so it is in the same order as the items are originally added.
        Collections.reverse(solutionItems);

        String summary = "";
        for (Item item : solutionItems) {
            summary += item.getInformation() + "\n";
        }

        //Remove the last newline character
        //summary.replaceAll("\n$", "");

        summary = summary.trim();

        return summary;

    }



    /**
     * Get the value for the requested item index and capacity index. If either index have gone
     * out of index, return negative infinity.
     * @param itemIndex
     * @param capacityIndex
     * @return
     */
    private double getValue(int itemIndex, int capacityIndex) {
        //itemIndex >= numItems because there is the item that acts as a buffer to represent
        //the case of having no items, offsets the indexes by one.
        if(itemIndex >= numItems || capacityIndex > maxCapacityUnits ) {
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
     * @param roundUp Roundup if we want
     * @return The equivalent amount of units.
     */
    public int calcUnits(Double amountDollars, boolean roundUp) {
        Double amountCents = amountDollars * 100.0;

        double result = Math.round(amountCents) / BASIC_UNIT;

        int units;

        if(roundUp) {
            units = (int) Math.round(result);
        } else {
            units = (int) Math.floor(result);
        }

        return units;
    }


    public void displayBoard() {
        for(double[] row : board) {
            for(double element : row) {
                System.out.print(element);
                System.out.print("\t");
            }
            System.out.println();
        }

        //System.out.println(Arrays.deepToString(board));
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Item object used for the zero one knapsack algorithm. Used to encapsulate the needed
     * information and its cost.
     */
    public class Item {

        private String information;
        private Double cost;
        private Double value;
        private int costUnits;

        public Item(String information, Double cost, int costUnits, Double value) {
            this.information = information;
            this.cost = cost;
            this.costUnits = costUnits;
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

        public void setCostUnits(int costUnits) {
            this.costUnits = costUnits;
        }

        public int getCostUnits() {
            return costUnits;
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



        @Override
        public String toString() {
            return String.format("Info: %s\nCost: %f\nCost in Units: %d\nValue: %f",
                    this.information, this.cost, this.costUnits, this.value);
        }

        @Override
        public boolean equals(Object o) {

            Item item = (Item) o;

            if((this.cost.equals(item.cost))
                    && (this.costUnits == item.costUnits)
                    && (this.information == item.information)
                    && (this.value.equals(item.value))) {
                return true;
            } else {
                return false;
            }
        }

    }

}
