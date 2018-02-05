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

    public static final double MATH_TOLERANCE = 1e-9;

    //The different available units which determines the accuracy of the solution.
    //All values will be rounded to 1, 10 or 100 cent accuracy. The higher the accuracy the more
    //resource intensive the zero one knapsack algorithm will be.
    public static final Double HIGH_ACCURACY_UNIT = 1.0; // 1 cent accuracy
    public static final Double MEDIUM_ACCURACY_UNIT = 10.0; // 10 cents accuracy
    public static final Double LOW_ACCURACY_UNIT = 100.0; // 1 dollar accuracy

    //Stores the summary to all the list of items that are solutions to the algorithm
    private ArrayList<String> solutions = new ArrayList<>();

    private ArrayList<Item> items;
    private double[][] board; //Used in the algorithm
    boolean[][] visitedBoard; //Used to keep track which positions have been visited already for a DFS
    private Double budget; //The maximum weight that can be taken i.e. the budget in dollars
    public final static Double DEFAULT_VALUE = 1.0;

    private int numItems;
    private int maxCapacityUnits;

    // One unit is equal to the amount of cents the variable holds.
    private Double basicUnit = HIGH_ACCURACY_UNIT;

    /**
     *
     * @param budget The maximum weight that can be taken i.e. the budget in dollars
     */
    public ZeroOneKnapsack(Double budget) {
        this.budget = budget;
    }

    /**
     * Change the accuracy of basicUnit variable (in cents) which is used in calculating how much
     * an amount in terms of 'basic units'.
     * @param accuracy The amount in cents a unit will be. Use the defined constants.
     *                 HIGH_ACCURACY_UNIT, MEDIUM_ACCURACY_UNIT or LOW_ACCURACY_UNIT
     */
    public void setUnitAccuracy(Double accuracy) {
        basicUnit = accuracy;
    }

    /**
     * @return Get the current value in cents a unit is.
     */
    public Double getUnitAccuracy() {
        return basicUnit;
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
        //Size includes the 'item' to represent the situation where no items are part of the
        //solution. Offsets the index by +1. E.g. 4 added items results in the items.size() to be 5
        numItems = items.size();

        //Round down the budget, if we round up we might end up saying we have more money than
        //our budget actually is.
        maxCapacityUnits = calcUnits(budget, false);

        board = new double[numItems][maxCapacityUnits + 1];

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
        //the situation of no items being added yet in the algorithm. It offsets the indexes by one.
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
            summary += item.getInformation() + Summary.ITEM_DELIMITER;
        }

        //Remove the last item delimeter as it shouldn't be there since there isn't another
        //item following it.
        summary = summary.replaceAll(Summary.ITEM_DELIMITER + "$", "");

        return summary;

    }

    /*
    public String solve() {
        startKnapsack();
        createNetwork();

        return summary;

    }*/

    /**
     * Create a network of items used to find all solutions when reconstructing the solution from
     * the board. When there is a tie between the situations of 'adding an item' and 'not adding
     * an item' we have two possible solutions.
     */

    private void reconstructSolutions() {
        Stack stack = new Stack();
        //Used to keep track which positions have been visited already for a DFS
        visitedBoard = new boolean[numItems][maxCapacityUnits + 1];

        //Start at the bottom right corner
        int itemIndex = numItems - 1; //There is the item acting as a buffer that represents
        //the situation of no items being added yet in the algorithm. It offsets the indexes by one.
        //e.g. is numItems is 5, then were 4 added items and one 'buffer item'.
        int capacityIndex = maxCapacityUnits;
        System.out.println("Starting itemIndex: " + itemIndex);
        System.out.println("Starting capacityIndex: " + capacityIndex);

        //Set up the starting Position in the stack
        stack.push(new Position(itemIndex, capacityIndex));
        Position nextPosition = calcNextPosition(itemIndex, capacityIndex);

        System.out.println("First next position: " + nextPosition.toString());

        //Keep running until we found all the solutions (happens when the stack is empty)
        while(!stack.isEmpty()) {
            if(nextPosition.getItemIndex() == 0) {
                //Next position will reach the bottom row, need to now calc the solution for this branch
                //i.e. the positions stored in the stack
                System.out.println("Reached the 0th row");
                stack.push(nextPosition);
                System.out.println(stack);
                return;
            } else if (nextPosition == null) {
                System.out.println("nextPosition is null");
                //Can not keep going towards the 0th row need to go back
                itemIndex += 1;
            } else {
                System.out.println("Found an actual next position to investigate.");
                //Has a next position to move to, add it to the stack and find the next Position
                //it could move to in the DFS to reconstruct a solution (out of multiple solutions)
                stack.push(nextPosition);

                //Move to the position
                itemIndex -= 1;
                assert (itemIndex == nextPosition.getItemIndex()) :
                        "The next position is not the next row above.";
                capacityIndex = nextPosition.getCapacityIndex();

                //has visited the position we are currently at
                visitedBoard[itemIndex][capacityIndex] = true;

                System.out.println("currentItemIndex: " + itemIndex + " | currentCapacityIndex: " + capacityIndex);

                //Find if there is a next position to go to.
                nextPosition = calcNextPosition(itemIndex, capacityIndex);
                System.out.println("Next position: " + nextPosition.toString());
            }


        }

        /*
        while(itemIndex > 0 && capacityIndex > 0) {
            if(getValue(itemIndex, capacityIndex) != getValue(itemIndex - 1, capacityIndex)) {
                //Item was added to the knapsack
                nextNode.setValue(itemIndex);
                addedItemIndexes.add(itemIndex);

                capacityIndex -= items.get(itemIndex).getCostUnits();
            }
            itemIndex -= 1; //Move down to the next item to see if it was added
        }*/

    }

    /**
     * Gives the next position in the board used in the zero one knapsack when reconstructing
     * the solution. Moves in the direction from the bottom right corner (last item added) to the
     * first row (no items added).
     * @param currentItemIndex The index of the current item we are investigating.
     * @param currentCapacityIndex The current capacity.
     * @return Returns a Position object to represent the next position on the board variable to go
     * to when doing a DFS. Returns null when there are no further positions to go to.
     */
    private Position calcNextPosition(int currentItemIndex, int currentCapacityIndex) {

        if(currentItemIndex == 0) {
            //Has reached the 0th row to represent no items being added, can not have a next position
            //return null and end early.
            return null;
        }

        ArrayList<Integer> possibleCapacityIndexes = calcNextCapacityIndexes(currentItemIndex, currentCapacityIndex);

        System.out.println("Possible capacity indexes: " + possibleCapacityIndexes);

        Position nextPosition = null;

        int nextItemIndex = currentItemIndex - 1;

        for(int nextCapacityIndex : possibleCapacityIndexes) {
            if(!isPositionVisited(nextItemIndex, nextCapacityIndex)) {
                //Found a valid position in continuing this branch in the DFS. End the loop early.
                return new Position(nextItemIndex, nextCapacityIndex);
            }
        }
        return nextPosition;
    }

    /**
     * Check the specified position on the board has not been yet when reconstructing the
     * solution(s).
     *
     * @param itemIndex The index for the board's row.
     * @param capacityIndex The index for the board's column.
     * @return
     */
    private boolean isPositionVisited(int itemIndex, int capacityIndex) {
        if(visitedBoard[itemIndex][capacityIndex] == false) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Used in finding multiple solutions to the zero one knapsack. When reconstructing the solution
     * the algorithm is 'reversed' and it is checked what items can be added to the knapsack and
     * what is the next path to follow. Will check if situations were possible; if the specified
     * item was not added and if the item was added. Will return the appropriate capacityIndex
     * values for the possible situations. Do not need to return the new itemIndex as it always
     * decreases by one, since in the zero one knapsack algorithm it always increases by one.
     *
     * @param  currentItemIndex The index of the item we are investigating.
     * @param currentCapacityIndex The total capacity/cost of all the items added so far.
     * @return Integer array of all possible capacityIndex values for the next position we can investigate in the (currentItemIndex - 1)th row.
     */
    private ArrayList<Integer> calcNextCapacityIndexes(int currentItemIndex, int currentCapacityIndex) {
        ArrayList<Integer> solutions = new ArrayList<>();
        double currentValue = getValue(currentItemIndex, currentCapacityIndex);
        double prevValue;

        //Check if it was possible the current item was not added and we continue to have the
        //previous set of items/capacity.
        prevValue = getValue(currentItemIndex -1,currentCapacityIndex); //Value if current item was not added
        if(Math.abs(currentValue - prevValue) < MATH_TOLERANCE) {
            //They are the same so it was possible the current item was not added
            solutions.add(currentCapacityIndex);
        }

        //Check if it was possible the current item was addded.
        int currentItemCost = items.get(currentItemIndex).getCostUnits();
        double currentItemValue = items.get(currentItemIndex).getValue();
        prevValue = getValue(currentItemIndex - 1,currentCapacityIndex - currentItemCost) + currentItemValue;
        if(Math.abs(currentValue - prevValue) < MATH_TOLERANCE) {
            //They are the same, so it was possible the current item was added to the solution
            solutions.add(currentCapacityIndex - currentItemCost);
        }

        return solutions;
    }

    /**
     * Get the value for the requested item index and capacity index. If either index have gone
     * out of index, return negative infinity.
     * @param itemIndex
     * @param capacityIndex
     * @return
     */
    private double getValue(int itemIndex, int capacityIndex) {
        String newLine = System.getProperty("line.separator");

        //itemIndex >= numItems because there is the item that acts as a buffer to represent
        //the case of having no items, offsets the indexes by one.
        if(itemIndex >= numItems || capacityIndex > maxCapacityUnits ) {
            throw new java.lang.Error("The index(es) are out of bounds." + newLine +
                    "itemIndex: " + itemIndex + " | numItems: " + numItems + newLine +
            "capacityIndex: " + capacityIndex + " | maxCapacityIndex: " + maxCapacityUnits);
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

        double result = Math.round(amountCents) / basicUnit;

        int units;

        if(roundUp) {
            units = (int) Math.ceil(result);
        } else {
            units = (int) Math.floor(result);
        }

        return units;
    }

    /**
     * Used in debugging. Display the contents of the grid used in the algorithm.
     */
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

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Double getBudget() {
        return budget;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public void setMaxCapacityUnits(int maxCapacityUnits) {
        this.maxCapacityUnits = maxCapacityUnits;
    }

    /**
     * Should only be used for testing to set up a specific circumstance.
     * @param board The new board.
     */
    public void setBoard(double[][] board) {
        this.board = board;
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
