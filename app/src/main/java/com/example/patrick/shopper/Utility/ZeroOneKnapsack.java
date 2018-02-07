package com.example.patrick.shopper.Utility;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
    //Use it as a set in the case where the user adds 5 identical items and there are multiple
    //solutions which have the same type and quantity of that item but different instances of them.
    // E.g. Budget 1 dollar, user added 3 lollipops each 1 dollar. Three solutions are given
    // where each solution is a lollipop
    private Set<String> solutions = new HashSet<>();

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
     *
     * Finds only one of the solutions.
     *
     * @return ArrayList of the item's indexes from items.
     */
    @Deprecated
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
     *
     * @param indexes The indexes from the elements in the ArrayList items.
     * @return An ArrayList of the items that matches to the provided indexes.
     */
    private ArrayList<Item> getItemsFromItemIndexes(ArrayList<Integer> indexes) {
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
        ArrayList<Item> solutionItems = getItemsFromItemIndexes(solutionIndexes);

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
    public String solve() {new Position(1,0),
        startKnapsack();
        createNetwork();

        return summary;

    }*/

    /**
     * Reconstruct all the solution by traversing the board.
     *
     * First will mark all positions in the board that are part of any solution.
     *
     * Next will use dynamic programming to save the solution set of Positions starting at each
     * visited point.
     *
     * When there is a tie between the situations of 'adding an item' and 'not adding an item' we
     * know that we have multiple solutions.
     *
     * At the end, the bottom right position will have all the sets of Positions that can give a
     * solution. Calculate the final list(s) and remove the duplicates.
     */
    private ArrayList<String> reconstructSolutions() {
        //When a 'next' Position or object or index is mentioned. It is related to the next ___
        //we get if we were to continue the pattern of reconstructing the solution.

        ArrayList<Position> visitedPositions = markSolutionPositions();

        //Using dynamic problem, at each cell in the board below store all the visited Position
        //objects needed to reconstruct the solution starting from that area.
        ArrayList<ArrayList<Set<ArrayList<Position>>>> solutionBoard = new ArrayList<>();
        for(int rowIndex = 0; rowIndex < numItems; rowIndex++) {
            solutionBoard.add(new ArrayList<Set<ArrayList<Position>>>());
            for(int columnIndex = 0; columnIndex < maxCapacityUnits + 1; columnIndex++) {
                solutionBoard.get(rowIndex).add(new HashSet<ArrayList<Position>>());
            }
        }

        //For each visited position find the all the Positions involved in reconstructing the
        //solution at that point. Save them in the corresponding cell in the solutionBoard
        for(Position visitedPos : visitedPositions) {

            int posItemIndex = visitedPos.getItemIndex();
            int posCapacityIndex = visitedPos.getCapacityIndex();

            ArrayList<Integer> nextCapacityIndexes = calcNextCapacityIndexes(posItemIndex, posCapacityIndex);
            //If the size is 0 then there are no next positions to go to.
            //If the size is 1 then there is only one next position to go to, hence same amount of solutions.
            //If the size is 2 then there are 2 positions to go to next, hence adds more solutions.
            int numIndexes = nextCapacityIndexes.size();

            if(numIndexes == 0) {
                //No next position to go to during reconstructing the solution. This means we are
                //at the 0th row in the board variable. The Set<ArrayList<Position>> at the
                //corresponding row and column in the solutionBoard do not have any
                //ArrayList<Position> yet. Need to create a new one with the current indexes
                //in a Position object. So far only this Position is part of the reconstruction of
                //a solution.
                Set setOfPositionArraysForSolutions = solutionBoard.get(posItemIndex).get(posCapacityIndex);

                //Will contain all the Positions that were included in the reconstruction of a
                //single solution.
                ArrayList<Position> positionArrayForSolution = new ArrayList<Position>(Arrays.asList(new Position(posItemIndex, posCapacityIndex)));
                setOfPositionArraysForSolutions.add(positionArrayForSolution);

            } else {
                //There is one or two Position we could go to next from the current visitedPos
                //For each next Position, retrieve all the arrays of Position objects and add the
                //current position to each of them. Save set of all these arrays at the
                //corresponding cell in solutionBoard. This represents all the partial solutions
                //that you can get from the current visitedPos.

                int nextItemIndex = posItemIndex - 1;
                Set currentSetOfPositionArraysForSolutions = solutionBoard.get(posItemIndex).get(posCapacityIndex);
                Position currentPosition = new Position(posItemIndex, posCapacityIndex);

                for(int nextCapacityIndex : nextCapacityIndexes) {

                    Set nextSetOfPositionArraysForSolutions = solutionBoard.get(nextItemIndex).get(nextCapacityIndex);

                    for (Iterator<ArrayList<Position>> iterator = nextSetOfPositionArraysForSolutions.iterator(); iterator.hasNext(); ) {
                        ArrayList<Position> nextArrayOfPositionsForSolution = iterator.next();

                        //Add on the current position to the array of Position from the next array,
                        //as it's also part of the solution reconstruction starting from this position
                        ArrayList<Position> currentArrayOfPositionsForSolution = (ArrayList<Position>) nextArrayOfPositionsForSolution.clone();
                        currentArrayOfPositionsForSolution.add(currentPosition);

                        currentSetOfPositionArraysForSolutions.add(currentArrayOfPositionsForSolution);
                    }
                }

            }

            System.out.println();
            System.out.println();
        }

        //Take set of all arrays of Positions at the bottom right corner in the solutionBoard.
        //Each array represents a reconstruction for a single solution. Calculate the final lists
        //and summarize them all.
        int lastRowIndex = numItems - 1;
        int lastColumnIndex = maxCapacityUnits;
        Position currentPosition = new Position(lastRowIndex, lastColumnIndex);

        Set setOfPositionArraysForFinalSolutions = solutionBoard.get(lastRowIndex).get(lastColumnIndex);

        ArrayList<Integer> nextCapacityIndexes = calcNextCapacityIndexes(lastRowIndex, lastColumnIndex);

        for(int nextCapacityIndex : nextCapacityIndexes) {
            int nextItemIndex = lastRowIndex - 1;

            Set nextSetOfPositionArraysForSolutions = solutionBoard.get(nextItemIndex).get(nextCapacityIndex);

            for (Iterator<ArrayList<Position>> iterator = nextSetOfPositionArraysForSolutions.iterator(); iterator.hasNext(); ) {
                ArrayList<Position> nextArrayOfPositionsForSolution = iterator.next();

                //Add on the current position to the array of Position from the next array,
                //as it's also part of the solution reconstruction starting from this position
                ArrayList<Position> currentArrayOfPositionsForSolution = (ArrayList<Position>) nextArrayOfPositionsForSolution.clone();
                currentArrayOfPositionsForSolution.add(currentPosition);

                setOfPositionArraysForFinalSolutions.add(currentArrayOfPositionsForSolution);
            }
        }

        //Store all the list summaries in this array
        ArrayList<String> solutionSummaries = new ArrayList<>();

        //Set used to remove duplicate solutions
        Set<String> setSolutionSummaries = new HashSet<>();

        for(Iterator<ArrayList<Position>> iterator = setOfPositionArraysForFinalSolutions.iterator(); iterator.hasNext(); ) {
            ArrayList<Position> positions = iterator.next();
            ArrayList<Integer> itemIndexes = null;

            try {
                //Store the indexes of all the items included in the final solution
                itemIndexes = retrieveItemIndexesInSolution(positions);
            } catch (Exception e) {
                e.printStackTrace();
            }

            ArrayList<Item> maximizedItems = getItemsFromItemIndexes(itemIndexes);
            String summarizedMaximizedList = Summary.summarizeKnapsackItems(maximizedItems);
            setSolutionSummaries.add(summarizedMaximizedList);
        }

        String[] tempArrayToBeConverted =  setSolutionSummaries.toArray(new String[setSolutionSummaries.size()]);
        solutionSummaries = new ArrayList<>(Arrays.asList(tempArrayToBeConverted));

        return solutionSummaries;
    }

    /**
     * Go through the array of positions and extract all the indexes for items that are part of the
     * solution.
     *
     * A Position has an itemIndex property.
     *
     * @param positions All the Position that were included in a list's reconstruction.
     * @return Array of indexes for items that are included in the maximized list's solution.
     */
    private ArrayList<Integer> retrieveItemIndexesInSolution(ArrayList<Position> positions) throws Exception {
        //If an item as been added then that means the capacity will change.

        ArrayList<Integer> indexSolutions = new ArrayList<>();
        //Start at the Position in the 0th row to represent the situation of not having added
        //any items yet. Any change from this Position's capacity means that the first item has
        //been added.
        int firstPositionItemIndex = 0;
        int numPositions = positions.size();
        int nextItemIndex;
        int nextCapacityIndex;

        if(numPositions == 0) {
            throw new Exception("An invalid argument was provided, the array needs to have Position elements in it.");
        }


        //Sort it in order such that the itemIndexes are in increasing order, now any change
        //in capacity (i.e. capacityIndexes) logically means that an item has been added
        positions = sortPositionArray(positions);

        int currentCapacityIndex = positions.get(firstPositionItemIndex).getCapacityIndex();

        for(int i = 1; i < numPositions; i++) {
            nextItemIndex = positions.get(i).getItemIndex();
            nextCapacityIndex = positions.get(i).getCapacityIndex();

            //The capacity will change, hence the item at nextItemIndex is aded.
            if(currentCapacityIndex != nextCapacityIndex) {
                assert (currentCapacityIndex < nextCapacityIndex) : new Error("somethign went wrong, Position array possibly not sorted properly.");

                //The capacity has increased, therefore the current item has been added
                indexSolutions.add(nextItemIndex);

            }

            currentCapacityIndex = nextCapacityIndex;
        }

        return indexSolutions;

    }

    /**
     * Mark all positions that are part of any solution's reconstruction.
     *
     * @return Array of Positions that are part of any solution sorted in order. First by itemIndex
     * and then by capacityIndex.
     */
    private ArrayList<Position> markSolutionPositions() {
        ArrayList<Position> visitedPositions = new ArrayList<>();
        Stack stack = new Stack();
        //Used to keep track which positions have been visited already for a DFS
        visitedBoard = new boolean[numItems][maxCapacityUnits + 1];

        //Start at the bottom right corner
        int itemIndex = numItems - 1; //There is the item acting as a buffer that represents
        //the situation of no items being added yet in the algorithm. It offsets the indexes by one.
        //e.g. is numItems is 5, then were 4 added items and one 'buffer item'.
        int capacityIndex = maxCapacityUnits;
        //System.out.println("Starting itemIndex: " + itemIndex);
        //System.out.println("Starting capacityIndex: " + capacityIndex);

        //Set up the starting Position in the stack
        stack.push(new Position(itemIndex, capacityIndex));
        visitedPositions.add(new Position(itemIndex, capacityIndex));
        visitedBoard[itemIndex][capacityIndex] = true;
        Position nextPosition = calcNextPosition(itemIndex, capacityIndex);

        //System.out.println("First next position: " + nextPosition.toString());

        //Mark all the visited positions using DFS.
        while(!stack.isEmpty()) {
            //This check needs to be first otherwise the rest of the checks will fail since we can't
            //use a method on a null object.
            if(nextPosition == null && stack.getSize() == 1) {
                //The stack only has the original position left in the stack and all
                stack.pop();

                visitedPositions = sortPositionArray(visitedPositions);
                return visitedPositions;
                //positions have been visited.
            } else if (nextPosition == null) {
                //System.out.println("nextPosition is null");
                //Can not keep going towards the 0th row need to go back
                //System.out.println("Previous index: " + itemIndex);
                stack.pop();

                //Peek at the top of the stack to re-position ourselves on the board. From this
                //position we will try and see if there are any next positions we can move to
                Position peekedPos = stack.peek();

                itemIndex += 1;
                assert (itemIndex == peekedPos.getItemIndex()) : new Error("Somethign went fucky fix it");
                capacityIndex = peekedPos.getCapacityIndex();

                //System.out.println("Index after incrementing because nextPosition was null: " + itemIndex);
                //System.out.print(stack);
                //System.out.println();

                nextPosition = calcNextPosition(itemIndex, capacityIndex);


            } else if(nextPosition.getItemIndex() == 0) {
                //Next position will reach the bottom row, add it to the stack
                //System.out.println("Reached the 0th row");
                stack.push(nextPosition);

                //Have reached/moved to the bottom row now
                itemIndex = nextPosition.getItemIndex();
                capacityIndex = nextPosition.getCapacityIndex();

                //has visited the position we are currently at
                visitedBoard[itemIndex][capacityIndex] = true;
                visitedPositions.add(new Position(itemIndex, capacityIndex));

                //System.out.println("\n");
                //System.out.println(stack);
                //System.out.println("\n\n");

                //System.out.println("Solutions to far: " + solutions.toString());

                //The next position should be null which results in us going back up the rows
                //to explore a different branch
                nextPosition = calcNextPosition(itemIndex, capacityIndex);
                //System.out.println("Next position next: !!!!!!!!!!!!!!!!!!!!!!!");
                //System.out.println(nextPosition);
                assert(nextPosition == null) : new Error("The next position is not null, which means the branch has not ended.");

            } else {
                //System.out.println("Found an actual next position to investigate.");
                //Has a next position to move to, add it to the stack and find the next Position
                //it could move to in the DFS
                stack.push(nextPosition);

                //Move to the position
                itemIndex -= 1;
                assert (itemIndex == nextPosition.getItemIndex()) :
                        "The next position is not the next row above.";
                capacityIndex = nextPosition.getCapacityIndex();

                //has visited the position we are currently at
                visitedBoard[itemIndex][capacityIndex] = true;
                visitedPositions.add(new Position(itemIndex, capacityIndex));

                //System.out.println("currentItemIndex: " + itemIndex + " | currentCapacityIndex: " + capacityIndex);

                //Find if there is a next position to go to.
                nextPosition = calcNextPosition(itemIndex, capacityIndex);
                //System.out.println("Next position: " + nextPosition.toString());
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



        visitedPositions = sortPositionArray(visitedPositions);
        return visitedPositions;
    }

    /**
     * Sorted an ArrayList of positions in increasing order. First by the itemIndex and then by
     * the capacityIndex.
     *
     * @param posArray Array to sort.
     * @return Sorted array.
     */
    private ArrayList<Position> sortPositionArray(ArrayList<Position> posArray) {
        Collections.sort(posArray, new Comparator<Position>() {
            @Override
            public int compare(Position pos1, Position pos2) {
                int itemIndexComparison = pos1.getItemIndex() - pos2.getItemIndex();
                if(itemIndexComparison != 0) {
                    //Not equal can just return value
                    return itemIndexComparison;
                } else {
                    //itemIndex tied, need to do further comparisons
                    int capacityIndexComparison = pos1.getCapacityIndex() - pos2.getCapacityIndex();
                    return capacityIndexComparison;
                }
            }
        });
        return posArray;
    }



    /**
     * Through a DFS when reconstructing a solution, we have reached the 0th row through a path of
     * Positions in the stack which contains a solution.
     *
     * Will iterate through the Positions to find all the changes in capacityIndex, this means
     * that an item was added to the final list.
     *
     * @param stack Stack contains Positions which construct the path to a solution.
     */
    private String reconstructSolutionFromStack(Stack stack) {
        Position nextPosition;
        int nextCapacityIndex;
        int nextItemIndex;

        //Contains all the indexes of items that are part of the final solution
        ArrayList<Integer> indexSolutions = new ArrayList<>();

        //First position popped is the bottom position because it's in the 0th row from the board
        Position bottomPosition = stack.pop();

        int currentCapacityIndex = bottomPosition.getCapacityIndex();
        int currentItemIndex = bottomPosition.getItemIndex();

        while(!stack.isEmpty()) {
            nextPosition = stack.pop();

            nextCapacityIndex = nextPosition.getCapacityIndex();
            nextItemIndex = nextPosition.getItemIndex();

            if(nextCapacityIndex != currentCapacityIndex) {
                //The item at nextItemIndex was added for the capacity to have changed
                indexSolutions.add(nextItemIndex);
            }

            currentItemIndex = nextItemIndex;
            currentCapacityIndex = nextCapacityIndex;
        }

        //Retrieve the corresponding items from their indexes and find the summary for the solution
        //then add it to the set containing all solutions.
        ArrayList<Item> solutionItems = getItemsFromItemIndexes(indexSolutions);

        String solutionSummary = Summary.summarizeKnapsackItems(solutionItems);

        return solutionSummary;

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

        //System.out.println("Possible capacity indexes: " + possibleCapacityIndexes);

        Position nextPosition = null;

        int nextItemIndex = currentItemIndex - 1;

        for(int nextCapacityIndex : possibleCapacityIndexes) {
            if(!isPositionVisited(nextItemIndex, nextCapacityIndex)) {
                //System.out.println("Chosen for the nextItemIndex, nextCapacityIndex: " + nextItemIndex + ", " + nextCapacityIndex);
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

        //If the the current itemIndex is 0, then we are at the 0th row in the board which
        //represents the situation of having not added any items. From this position there is no
        //next position on the board to move to in reconstructing the solution. Hence there
        //are no capcityIndexes to return. Return the empty array.
        if(currentItemIndex == 0) {
            return solutions;
        }

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
        int currentItemCostUnits = items.get(currentItemIndex).getCostUnits();
        double currentItemValue = items.get(currentItemIndex).getValue();

        assert (currentItemValue >= 0) : new Error("The cost should not be negative");

        prevValue = getValue(currentItemIndex - 1,currentCapacityIndex - currentItemCostUnits) + currentItemValue;
        if(Math.abs(currentValue - prevValue) < MATH_TOLERANCE) {
            //They are the same, so it was possible the current item was added to the solution
            solutions.add(currentCapacityIndex - currentItemCostUnits);
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

    public Set<String> getSolutions() {
        return solutions;
    }

    /**
     * Should only be used for testing to set up a specific circumstance.
     * @param board The new board.
     */
    public void setBoard(double[][] board) {
        this.board = board;
    }

    public void setVisitedBoard(boolean[][] visitedBoard) {
        this.visitedBoard = visitedBoard;
    }

    public boolean[][] getVisitedBoard() {
        return visitedBoard;
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
