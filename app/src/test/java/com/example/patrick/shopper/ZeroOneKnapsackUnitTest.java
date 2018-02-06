package com.example.patrick.shopper;

import com.example.patrick.shopper.Utility.Position;
import com.example.patrick.shopper.Utility.Stack;
import com.example.patrick.shopper.Utility.Summary;
import com.example.patrick.shopper.Utility.ZeroOneKnapsack;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by patrick on 18/01/18.
 */

public class ZeroOneKnapsackUnitTest {

    private final Double BUDGET = 5.00;
    private ZeroOneKnapsack knapsack;
    private double  DEFAULT_VALUE = 1.0;
    private final String calcNextCapacityIndexesMethodName = "calcNextCapacityIndexes";
    private final String reconstructSolutionsMethodName = "reconstructSolutions";

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setUp() {
        knapsack = new ZeroOneKnapsack(BUDGET);
        knapsack.initKnapsack();
    }

    /**
     * Test the boundary if we get the expected result when we enter a budget of 0.
     */
    @Test
    public void calcUnitsZeroTestHighAccuracy() {
        double amount = 0.0;

        knapsack.setUnitAccuracy(ZeroOneKnapsack.HIGH_ACCURACY_UNIT); //1 cent accuracy
        assertEquals(0, knapsack.calcUnits(amount, true));
        assertEquals(0, knapsack.calcUnits(amount, false));
    }

    /**
     * Test the boundary if we get the expected result when we enter a budget of 0.
     */
    @Test
    public void calcUnitsZeroTestMediumAccuracy() {
        double amount = 0.0;

        knapsack.setUnitAccuracy(ZeroOneKnapsack.MEDIUM_ACCURACY_UNIT); //10 cent accuracy
        assertEquals(0, knapsack.calcUnits(amount, true));
        assertEquals(0, knapsack.calcUnits(amount, false));
    }
    /**
     * Test the boundary if we get the expected result when we enter a budget of 0.
     */
    @Test
    public void calcUnitsZeroTestLowAccuracy() {
        double amount = 0.0;

        knapsack.setUnitAccuracy(ZeroOneKnapsack.LOW_ACCURACY_UNIT); //100 cent accuracy
        assertEquals(0, knapsack.calcUnits(amount, true));
        assertEquals(0, knapsack.calcUnits(amount, false));
    }


    /**
     * Test if we get the correct result if we enter an amount that does not need rounding.
     */
    @Test
    public void calcUnitsNormalTestHighAccuracy() {
        knapsack.setUnitAccuracy(ZeroOneKnapsack.HIGH_ACCURACY_UNIT); //1 cent accuracy

        double amount = 5.50;
        assertEquals(550, knapsack.calcUnits(amount, true));
        assertEquals(550, knapsack.calcUnits(amount, false));

        double amount2 = 5.00;
        assertEquals(500, knapsack.calcUnits(amount2, true));
        assertEquals(500, knapsack.calcUnits(amount2, false));

        double amount3 = 5.40;
        assertEquals(540, knapsack.calcUnits(amount3, true));
        assertEquals(540, knapsack.calcUnits(amount3, false));
    }

    /**
     * Test if we get the correct result if we enter an amount that does not need rounding.
     */
    @Test
    public void calcUnitsNormalTestMediumAccuracy() {
        knapsack.setUnitAccuracy(ZeroOneKnapsack.MEDIUM_ACCURACY_UNIT); //10 cent accuracy

        double amount = 5.50;
        assertEquals(55, knapsack.calcUnits(amount, true));
        assertEquals(55, knapsack.calcUnits(amount, false));

        double amount2 = 5.00;
        assertEquals(50, knapsack.calcUnits(amount2, true));
        assertEquals(50, knapsack.calcUnits(amount2, false));

        double amount3 = 5.40;
        assertEquals(54, knapsack.calcUnits(amount3, true));
        assertEquals(54, knapsack.calcUnits(amount3, false));
    }

    /**
     * Test if we get the correct result if we enter an amount that does not need rounding.
     */
    @Test
    public void calcUnitsNormalTestLowAccuracy() {
        knapsack.setUnitAccuracy(ZeroOneKnapsack.LOW_ACCURACY_UNIT); //100 cent accuracy

        double amount = 5.50;
        assertEquals(6, knapsack.calcUnits(amount, true));
        assertEquals(5, knapsack.calcUnits(amount, false));

        double amount2 = 5.00;
        assertEquals(5, knapsack.calcUnits(amount2, true));
        assertEquals(5, knapsack.calcUnits(amount2, false));

        double amount3 = 5.40;
        assertEquals(6, knapsack.calcUnits(amount3, true));
        assertEquals(5, knapsack.calcUnits(amount3, false));
    }

    /**
     * Test if we get the correct result if we enter an amount that needs rounding.
     */
    @Test
    public void calcUnitsTestHighAccuracy() {
        knapsack.setUnitAccuracy(ZeroOneKnapsack.HIGH_ACCURACY_UNIT); //1 cent accuracy

        double amount = 5.55;
        assertEquals(555, knapsack.calcUnits(amount, true));
        assertEquals(555, knapsack.calcUnits(amount, false));

        double amount2 = 5.54;
        assertEquals(554, knapsack.calcUnits(amount2, true));
        assertEquals(554, knapsack.calcUnits(amount2, false));

        double amount3 = 5.00;
        assertEquals(500, knapsack.calcUnits(amount3, true));
        assertEquals(500, knapsack.calcUnits(amount3, false));
    }

    /**
     * Test if we get the correct result if we enter an amount that needs rounding.
     */
    @Test
    public void calcUnitsTestMediumAccuracy() {
        knapsack.setUnitAccuracy(ZeroOneKnapsack.MEDIUM_ACCURACY_UNIT); //10 cent accuracy

        double amount = 5.55;
        assertEquals(56, knapsack.calcUnits(amount, true));
        assertEquals(55, knapsack.calcUnits(amount, false));

        double amount2 = 5.54;
        assertEquals(56, knapsack.calcUnits(amount2, true));
        assertEquals(55, knapsack.calcUnits(amount2, false));

        double amount3 = 5.00;
        assertEquals(50, knapsack.calcUnits(amount3, true));
        assertEquals(50, knapsack.calcUnits(amount3, false));
    }

    /**
     * Test if we get the correct result if we enter an amount that needs rounding.
     */
    @Test
    public void calcUnitsTestLowAccuracy() {
        knapsack.setUnitAccuracy(ZeroOneKnapsack.LOW_ACCURACY_UNIT); //100 cent accuracy

        double amount = 5.55;
        assertEquals(6, knapsack.calcUnits(amount, true));
        assertEquals(5, knapsack.calcUnits(amount, false));

        double amount2 = 5.54;
        assertEquals(6, knapsack.calcUnits(amount2, true));
        assertEquals(5, knapsack.calcUnits(amount2, false));

        double amount3 = 5.00;
        assertEquals(5, knapsack.calcUnits(amount3, true));
        assertEquals(5, knapsack.calcUnits(amount3, false));
    }

    /**
     * Test if we are able to add items properly.
     */
    @Test
    public void addItemsTest() {
        String info1 = "A";
        String info2 = "B";
        String info3 = "C";

        double cost1 = 1.00;
        double cost2 = 1.10;
        double cost3 = 1.20;

        ZeroOneKnapsack.Item modelItem1 = knapsack.new Item(info1, cost1, knapsack.calcUnits(cost1, true), DEFAULT_VALUE);
        ZeroOneKnapsack.Item modelItem2 = knapsack.new Item(info2, cost2, knapsack.calcUnits(cost2, true), DEFAULT_VALUE);
        ZeroOneKnapsack.Item modelItem3 = knapsack.new Item(info3, cost3, knapsack.calcUnits(cost3, true), DEFAULT_VALUE);

        knapsack.addItem(info1, cost1);
        knapsack.addItem(info2, cost2);
        knapsack.addItem(info3, cost3);

        ArrayList<ZeroOneKnapsack.Item> retrievedItems = knapsack.getItems();

        //At index 0 there is an Item object that is used to represent the situation of
        //no items being added in the algorithm. Indexes > 0 are the actual items.
        assertEquals(modelItem1, retrievedItems.get(1));
        assertEquals(modelItem2, retrievedItems.get(2));
        assertEquals(modelItem3, retrievedItems.get(3));
    }

    /**
     * Test if the correct items can be retrieved.
     */
    @Test
    public void solveTest() {
        String itemAInfo = "A" + Summary.INFO_DELIMITER + "1.00" + Summary.INFO_DELIMITER + "1";
        String itemBInfo = "B" + Summary.INFO_DELIMITER + "1.20" + Summary.INFO_DELIMITER + "1";
        String itemCInfo = "C" + Summary.INFO_DELIMITER + "5.00" + Summary.INFO_DELIMITER + "1";
        String itemDInfo = "D" + Summary.INFO_DELIMITER + "2.10" + Summary.INFO_DELIMITER + "1";
        String itemEInfo = "E" + Summary.INFO_DELIMITER + "0.90" + Summary.INFO_DELIMITER + "1";
        String itemFInfo = "F" + Summary.INFO_DELIMITER + "0.70" + Summary.INFO_DELIMITER + "1";

        knapsack.addItem(itemAInfo, 1.00);
        knapsack.addItem(itemBInfo, 1.20);
        knapsack.addItem(itemCInfo, 5.00);
        knapsack.addItem(itemDInfo, 2.10);
        knapsack.addItem(itemEInfo, 0.90);
        knapsack.addItem(itemFInfo, 0.70);

        //System.out.println(knapsack.getItems());

        String modelSolution = itemAInfo + Summary.ITEM_DELIMITER +
                itemBInfo + Summary.ITEM_DELIMITER + itemDInfo + Summary.ITEM_DELIMITER +
                itemFInfo;

        String solution = knapsack.solve();

        //knapsack.displayBoard();

        //System.out.println(solution);

        assertTrue(modelSolution.equals(solution));
    }

    @Test
    public void solveTest2() {
        String itemInfo1 = "Item1;1.00;1;";
        String itemInfo2 = "Item2;2.00;1;";
        String itemInfo3 = "Item3;1.50;1";

        String modelSolution = itemInfo1 + Summary.ITEM_DELIMITER + itemInfo2 +
                Summary.ITEM_DELIMITER + itemInfo3;

        knapsack.setBudget(5.00);
        knapsack.addItem(itemInfo1, 1.00);
        knapsack.addItem(itemInfo2, 2.00);
        knapsack.addItem(itemInfo3, 1.50);

        String solution = knapsack.solve();

        //System.out.println("Solution: " + solution);

        assertTrue(solution.equals(modelSolution));
    }

    /**
     * Test if the algorithm gives the correct result when no items are added.
     */
    @Test
    public void solveTestNone() {
        String modelSolution = "";
        String solution = knapsack.solve();
        assertTrue(modelSolution.equals(solution));
    }

    /**
     * Test if the correct capacityIndexes can be returned when it was possible the item was
     * and was not added.
     */
    @Test
    public void calcNextCapacityIndexesTest() {
        double budget = 0.05;

        double[][] customBoard = new double[][]{
                {0,0,0,0,0,0},
                {0,0,1,1,1,1},
                {0,0,1,1,2,2},
                {0,0,1,1,2,2}
        };

        //Both situations should be possible and need to be considered
        // 1) The item at itemIndex 3 was not added
        // 2) The item at itemIndex 3 was added
        ArrayList<Integer> modelsolution = new ArrayList<>(Arrays.asList(5,2));


        knapsack.setBoard(customBoard);
        knapsack.setBudget(budget); //Want maxCapacityUnits to be 5
        knapsack.addItem("A", 0.02); //Want the costUnits to be 2
        knapsack.addItem("B", 0.02);
        knapsack.addItem("C", 0.03); //Want the costUnits to be 3
        knapsack.setNumItems(knapsack.getItems().size()); //Otherwise we get an exception thrown
        knapsack.setMaxCapacityUnits(knapsack.calcUnits(budget, false));


        try {
            Object[] parameters = new Object[]{3, 5};

            Method m = knapsack.getClass().getDeclaredMethod(calcNextCapacityIndexesMethodName, int.class, int.class);
            m.setAccessible(true);

            ArrayList<Integer> solutions = (ArrayList<Integer>) m.invoke(knapsack, parameters);
            assertEquals(solutions, modelsolution);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    /**
     * Test if the correct capacityIndexes can be returned if the only possibility was the item
     * being added.
     */
    @Test
    public void calcNextCapacityIndexesTestAddedOnly() {
        double budget = 0.05;
        double[][] customBoard = new double[][]{
                {0,0,0,0,0,0},
                {0,0,1,1,1,1},
                {0,0,1,1,2,2},
                {0,0,1,1,2,2}
        };

        //Both situations should be possible and need to be considered
        // 1) The item at itemIndex 3 was not added
        // 2) The item at itemIndex 3 was added
        ArrayList<Integer> modelsolution = new ArrayList<>(Arrays.asList(3));

        knapsack.setBoard(customBoard);
        knapsack.setBudget(budget); //Want maxCapacityUnits to be 5
        knapsack.addItem("A", 0.02); //Want the costUnits to be 2
        knapsack.addItem("B", 0.02);
        knapsack.addItem("C", 0.03); //Want the costUnits to be 3
        knapsack.setNumItems(knapsack.getItems().size()); //Otherwise we get an exception thrown
        knapsack.setMaxCapacityUnits(knapsack.calcUnits(budget, false));

        try {
            Object[] parameters = new Object[]{2, 5};

            Method m = knapsack.getClass().getDeclaredMethod(calcNextCapacityIndexesMethodName, int.class, int.class);
            m.setAccessible(true);

            ArrayList<Integer> solutions = (ArrayList<Integer>) m.invoke(knapsack, parameters);
            assertEquals(solutions, modelsolution);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test if the correct capacityIndexes can be returned if the only possibility was the item
     * not being added.
     */
    @Test
    public void calcNextCapacityIndexesTestNotAddedOnly() {
        double budget = 0.05;
        double[][] customBoard = new double[][]{
                {0,0,0,0,0,0},
                {0,0,1,1,1,1},
                {0,0,1,1,2,2},
                {0,0,1,1,2,2}
        };

        //Both situations should be possible and need to be considered
        // 1) The item at itemIndex 3 was not added
        // 2) The item at itemIndex 3 was added
        ArrayList<Integer> modelsolution = new ArrayList<>(Arrays.asList(0));

        knapsack.setBoard(customBoard);
        knapsack.setBudget(budget); //Want maxCapacityUnits to be 5
        knapsack.addItem("A", 0.02); //Want the costUnits to be 2
        knapsack.addItem("B", 0.02);
        knapsack.addItem("C", 0.03); //Want the costUnits to be 3
        knapsack.setNumItems(knapsack.getItems().size()); //Otherwise we get an exception thrown
        knapsack.setMaxCapacityUnits(knapsack.calcUnits(budget, false));

        try {
            Object[] parameters = new Object[]{1, 0};

            Method m = knapsack.getClass().getDeclaredMethod(calcNextCapacityIndexesMethodName, int.class, int.class);
            m.setAccessible(true);

            ArrayList<Integer> solutions = (ArrayList<Integer>) m.invoke(knapsack, parameters);
            assertEquals(solutions, modelsolution);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test if the solutions can be reconstructed.
     */
    @Test
    public void reconstructSolutionsTest() {
        double budget = 0.05;
        Set solution = null;
        double[][] customBoard = new double[][]{
                {0,0,0,0,0,0},
                {0,0,1,1,1,1},
                {0,0,1,1,2,2},
                {0,0,1,1,2,2}
        };

        String itemAInfo = "A";
        String itemBInfo = "B";
        String itemCInfo = "C";

        Set<String> modelSolutions = new HashSet<>();
        modelSolutions.add(itemAInfo + Summary.ITEM_DELIMITER + itemBInfo);
        modelSolutions.add(itemAInfo + Summary.ITEM_DELIMITER + itemCInfo);
        modelSolutions.add(itemBInfo + Summary.ITEM_DELIMITER + itemCInfo);

        knapsack.setBoard(customBoard);
        knapsack.setBudget(budget); //Want maxCapacityUnits to be 5
        knapsack.addItem("A", 0.02); //Want the costUnits to be 2
        knapsack.addItem("B", 0.02);
        knapsack.addItem("C", 0.03); //Want the costUnits to be 3
        knapsack.setNumItems(knapsack.getItems().size()); //Otherwise we get an exception thrown
        knapsack.setMaxCapacityUnits(knapsack.calcUnits(budget, false));

        try {
            Method m = knapsack.getClass().getDeclaredMethod(reconstructSolutionsMethodName);
            m.setAccessible(true);
            m.invoke(knapsack);
            solution = knapsack.getSolutions();


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        assertEquals(solution, modelSolutions);
    }

    /**
     * Test if the method immediately returns null when the itemIndex (i.e. row in board) is 0.
     * There is no next position when we have reached the 0th index.
     */
    @Test
    public void calcNextPositionTestNull() {
        try {
            Method m = knapsack.getClass().getDeclaredMethod("calcNextPosition", int.class, int.class);
            m.setAccessible(true);
            Position pos = (Position) m.invoke(knapsack, 0, 1);
            assertEquals(pos, null);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test if given two options for the next Position it will return a correct option.
     */
    @Test
    public void calcNextPositionTest() {
        double budget = 0.05;

        double[][] customBoard = new double[][]{
                {0,0,0,0,0,0},
                {0,0,1,1,1,1},
                {0,0,1,1,2,2},
                {0,0,1,1,2,2}
        };

        //Set up such that it is as though certain positions have already been visited
        boolean[][] customVisitedBoard = new boolean[4][6];
        customVisitedBoard[3][5] = true;
        customVisitedBoard[2][5] = true;
        customVisitedBoard[1][3] = true;
        customVisitedBoard[0][1] = true;
        customVisitedBoard[2][2] = true;

        knapsack.setVisitedBoard(customVisitedBoard);

        knapsack.setBoard(customBoard);
        knapsack.setBudget(budget); //Want maxCapacityUnits to be 5
        knapsack.addItem("A", 0.02); //Want the costUnits to be 2
        knapsack.addItem("B", 0.02);
        knapsack.addItem("C", 0.03); //Want the costUnits to be 3
        knapsack.setNumItems(knapsack.getItems().size()); //Otherwise we get an exception thrown
        knapsack.setMaxCapacityUnits(knapsack.calcUnits(budget, false));

        knapsack.setBoard(customBoard);
        Position modelSolution = new Position(1,2);

        try {
            Method m = knapsack.getClass().getDeclaredMethod("calcNextPosition", int.class, int.class);
            m.setAccessible(true);
            Position pos = (Position) m.invoke(knapsack, 2, 2);
            assertEquals(pos, modelSolution);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test if given two options for the next Position but one has already been visited it will
     * choose the only available option.
     */
    @Test
    public void calcNextPositionTest2() {
        double budget = 0.05;

        double[][] customBoard = new double[][]{
                {0,0,0,0,0,0},
                {0,0,1,1,1,1},
                {0,0,1,1,2,2},
                {0,0,1,1,2,2}
        };

        //Set up such that it is as though certain positions have already been visited
        boolean[][] customVisitedBoard = new boolean[4][6];
        customVisitedBoard[3][5] = true;
        customVisitedBoard[2][5] = true;
        customVisitedBoard[1][3] = true;
        customVisitedBoard[0][1] = true;

        knapsack.setVisitedBoard(customVisitedBoard);

        knapsack.setBoard(customBoard);
        knapsack.setBudget(budget); //Want maxCapacityUnits to be 5
        knapsack.addItem("A", 0.02); //Want the costUnits to be 2
        knapsack.addItem("B", 0.02);
        knapsack.addItem("C", 0.03); //Want the costUnits to be 3
        knapsack.setNumItems(knapsack.getItems().size()); //Otherwise we get an exception thrown
        knapsack.setMaxCapacityUnits(knapsack.calcUnits(budget, false));

        knapsack.setBoard(customBoard);
        Position modelSolution = new Position(2,2);

        try {
            Method m = knapsack.getClass().getDeclaredMethod("calcNextPosition", int.class, int.class);
            m.setAccessible(true);
            Position pos = (Position) m.invoke(knapsack, 3, 5);
            assertEquals(pos, modelSolution);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test if given one option for the next Position it will return it.
     */
    @Test
    public void calcNextPositionTest3() {
        double budget = 0.05;

        double[][] customBoard = new double[][]{
                {0,0,0,0,0,0},
                {0,0,1,1,1,1},
                {0,0,1,1,2,2},
                {0,0,1,1,2,2}
        };

        boolean[][] customVisitedBoard = new boolean[4][6];

        knapsack.setVisitedBoard(customVisitedBoard);

        knapsack.setBoard(customBoard);
        knapsack.setBudget(budget); //Want maxCapacityUnits to be 5
        knapsack.addItem("A", 0.02); //Want the costUnits to be 2
        knapsack.addItem("B", 0.02);
        knapsack.addItem("C", 0.03); //Want the costUnits to be 3
        knapsack.setNumItems(knapsack.getItems().size()); //Otherwise we get an exception thrown
        knapsack.setMaxCapacityUnits(knapsack.calcUnits(budget, false));

        knapsack.setBoard(customBoard);
        Position modelSolution = new Position(1,3);

        try {
            Method m = knapsack.getClass().getDeclaredMethod("calcNextPosition", int.class, int.class);
            m.setAccessible(true);
            Position pos = (Position) m.invoke(knapsack, 2, 5);
            assertEquals(pos, modelSolution);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test if the correct items that is part of a solution can be worked out from the Positions
     * in a Stack. The positions represents the path done in a DFS used in reconstructing a
     * possible solution.
     */
    @Test
    public void reconstructSolutionFromStackTest() {
        try {
            knapsack.addItem("A", 2.00);
            knapsack.addItem("B", 2.00);
            knapsack.addItem("C", 3.00);

            String modelSolution = "A" + Summary.ITEM_DELIMITER + "B";

            Stack param = new Stack();
            param.push(new Position(3, 5));
            param.push(new Position(2, 5));
            param.push(new Position(1, 3));
            param.push(new Position(0, 1));

            Method m = knapsack.getClass().getDeclaredMethod("reconstructSolutionFromStack", Stack.class);
            m.setAccessible(true);
            String solution = (String) m.invoke(knapsack, param);
            assertEquals(solution, modelSolution);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
