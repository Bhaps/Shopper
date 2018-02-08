package com.example.patrick.shopper;

import android.text.style.AbsoluteSizeSpan;

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
import java.util.Collections;
import java.util.Comparator;
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

    Comparator<String> comparator = new Comparator<String>() {
        @Override
        public int compare(String s, String t1) {
            return s.compareTo(t1);
        }
    };


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

        String[] items = new String[]{itemAInfo, itemBInfo, itemCInfo,
                itemDInfo, itemEInfo, itemFInfo};

        String modelSolution = itemBInfo + Summary.ITEM_DELIMITER + itemDInfo + Summary.ITEM_DELIMITER + itemEInfo + Summary.ITEM_DELIMITER + itemFInfo + Summary.LIST_DELIMITER +
                itemAInfo + Summary.ITEM_DELIMITER + itemBInfo + Summary.ITEM_DELIMITER + itemDInfo + Summary.ITEM_DELIMITER + itemFInfo + Summary.LIST_DELIMITER +
                itemAInfo + Summary.ITEM_DELIMITER + itemDInfo + Summary.ITEM_DELIMITER + itemEInfo + Summary.ITEM_DELIMITER + itemFInfo + Summary.LIST_DELIMITER +
                itemAInfo + Summary.ITEM_DELIMITER + itemBInfo + Summary.ITEM_DELIMITER + itemEInfo + Summary.ITEM_DELIMITER + itemFInfo;

        String solution = knapsack.solve(items);
        assertMaximizedListsEqual(solution, modelSolution);
    }

    @Test
    public void solveTest2() {
        String itemInfo1 = "Item1" + Summary.INFO_DELIMITER + "1.00" + Summary.INFO_DELIMITER + "1";
        String itemInfo2 = "Item2" + Summary.INFO_DELIMITER + "2.00"  + Summary.INFO_DELIMITER + "1";
        String itemInfo3 = "Item3" + Summary.INFO_DELIMITER + "1.50" + Summary.INFO_DELIMITER + "1";

        String modelSolution = itemInfo1 + Summary.ITEM_DELIMITER + itemInfo2 +
                Summary.ITEM_DELIMITER + itemInfo3;

        knapsack.setBudget(5.00);

        String[] items = new String[]{itemInfo1, itemInfo2, itemInfo3};

        String solution = knapsack.solve(items);

        System.out.println(solution);
        System.out.println(modelSolution);

        assertMaximizedListsEqual(solution, modelSolution);
    }

    /**
     * Test if the correct maximized list can be calculated when an item has a quantity greater
     * than 1.
     */
    @Test
    public void solveTestDuplicateItems() {
        String itemInfo1 = "Item1" + Summary.INFO_DELIMITER + "1.00" + Summary.INFO_DELIMITER + "3";
        String itemInfo2 = "Item2" + Summary.INFO_DELIMITER + "2.00"  + Summary.INFO_DELIMITER + "1";

        String modelSolution = "Item1" + Summary.INFO_DELIMITER + "1.00" + Summary.INFO_DELIMITER + "2";

        String[] items = new String[]{itemInfo1, itemInfo2};

        knapsack.setBudget(2.00);
        knapsack.addItem(itemInfo1, 1.00);
        knapsack.addItem(itemInfo2, 2.00);

        String solution = knapsack.solve(items);

        //System.out.println("Solution: " + solution);

        System.out.println(modelSolution);
        System.out.println(solution);

        assertMaximizedListsEqual(solution, modelSolution);
    }

    /**
     * Test if the correct maximized list can be calculated when an item has a quantity greater
     * than 1.
     */
    @Test
    public void solveTestDuplicateItems2() {
        String itemInfo1 = "Item1" + Summary.INFO_DELIMITER + "1.00" + Summary.INFO_DELIMITER + "3";

        String modelSolution = "Item1" + Summary.INFO_DELIMITER + "1.00" + Summary.INFO_DELIMITER + "2";

        knapsack.setBudget(2.00);
        knapsack.addItem(itemInfo1, 1.00);

        String[] items = new String[]{itemInfo1};

        String solution = knapsack.solve(items);

        //System.out.println("Solution: " + solution);

        System.out.println(modelSolution);
        System.out.println(solution);

        assertMaximizedListsEqual(solution, modelSolution);
    }

    /**
     * Test if the algorithm gives the correct result when no items are added.
     */
    @Test
    public void solveTestNoItemsAdded() {
        String modelSolution = "";
        String solution = knapsack.solve(new String[0]);
        assertMaximizedListsEqual(solution, modelSolution);
    }

    /**
     * Test if the algorithm gives the correct result when all the items are above the budget.
     */
    @Test
    public void solveTestNoneUnderBudget() {
        String modelSolution = "";
        knapsack.setBudget(5.00);
        String itemA = "A" + Summary.INFO_DELIMITER + "5.01" + Summary.INFO_DELIMITER + "1";
        String itemB = "B" + Summary.INFO_DELIMITER + "5.01" + Summary.INFO_DELIMITER + "1";
        String[] items = new String[]{itemA, itemB};
        String solution = knapsack.solve(items);
        assertMaximizedListsEqual(solution, modelSolution);
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
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
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
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
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
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Test if the solutions can be reconstructed.
     */
    @Test
    public void reconstructSolutionsTest() {
        double budget = 0.05;
        ArrayList<String> solution = null;
        double[][] customBoard = new double[][]{
                {0,0,0,0,0,0},
                {0,0,1,1,1,1},
                {0,0,1,1,2,2},
                {0,0,1,1,2,2}
        };

        String itemAInfo = "A";
        String itemBInfo = "B";
        String itemCInfo = "C";

        ArrayList<String> modelSolutions = new ArrayList<>();
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
            ArrayList<String> retrievedSolutions = (ArrayList<String>) m.invoke(knapsack);

            assertTrue(retrievedSolutions.equals(modelSolutions));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Test if the solutions can be reconstructed when there are duplicate items. Without
     * getting duplicate solutions.
     */
    @Test
    public void reconstructSolutionsDuplicates() {
        double budget = 0.05;
        ArrayList<String> solution = null;

        double[][] customBoard = new double[][]{
                {0,0,0,0,0,0},
                {0,0,1,1,1,1},
                {0,0,1,1,2,2},
                {0,0,1,1,2,2}
        };

        String itemAInfo = "A";
        String itemBInfo = "B";
        String itemCInfo = "C";

        ArrayList<String> modelSolutions = new ArrayList<>();
        modelSolutions.add(itemAInfo + Summary.ITEM_DELIMITER + itemBInfo);
        modelSolutions.add(itemAInfo + Summary.ITEM_DELIMITER + itemCInfo);
        modelSolutions.add(itemBInfo + Summary.ITEM_DELIMITER + itemCInfo);

        //knapsack.setBoard(customBoard);

        knapsack.setBudget(budget); //Want maxCapacityUnits to be 5
        knapsack.addItem("A", 0.02); //Want the costUnits to be 2
        knapsack.addItem("B", 0.02);
        knapsack.addItem("C", 0.03); //Want the costUnits to be 3
        //knapsack.setNumItems(knapsack.getItems().size()); //Otherwise we get an exception thrown
        //knapsack.setMaxCapacityUnits(knapsack.calcUnits(budget, false));

        try {
            Method startKnapsackMethod = knapsack.getClass().getDeclaredMethod("startKnapsack");
            Method reconstructSolutionMethod = knapsack.getClass().getDeclaredMethod(reconstructSolutionsMethodName);
            startKnapsackMethod.setAccessible(true);
            reconstructSolutionMethod.setAccessible(true);
            startKnapsackMethod.invoke(knapsack);
            ArrayList<String> retrievedSolutions = (ArrayList<String>) reconstructSolutionMethod.invoke(knapsack);

            assertTrue(retrievedSolutions.equals(modelSolutions));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Test if the solutions can be reconstructed.
     */
    @Test
    public void reconstructSolutionsTest2() {
        double budget = 0.05;
        ArrayList<String> solution = null;
        double[][] customBoard = new double[][]{
                {0,0,0,0,0,0},
                {0,0,1,1,1,1},
                {0,0,1,1,2,2},
                {0,0,1,1,2,2},
                {0,1,1,2,2,3},
                {0,1,2,2,3,3}
        };

        String itemAInfo = "A";
        String itemBInfo = "B";
        String itemCInfo = "C";
        String itemDInfo = "D";
        String itemEInfo = "E";

        ArrayList<String> modelSolutions = new ArrayList<>();
        modelSolutions.add(itemAInfo + Summary.ITEM_DELIMITER + itemBInfo + Summary.ITEM_DELIMITER + itemDInfo);
        modelSolutions.add(itemAInfo + Summary.ITEM_DELIMITER + itemBInfo + Summary.ITEM_DELIMITER + itemEInfo);
        modelSolutions.add(itemAInfo + Summary.ITEM_DELIMITER + itemCInfo + Summary.ITEM_DELIMITER + itemDInfo);
        modelSolutions.add(itemAInfo + Summary.ITEM_DELIMITER + itemCInfo + Summary.ITEM_DELIMITER + itemEInfo);
        modelSolutions.add(itemBInfo + Summary.ITEM_DELIMITER + itemCInfo + Summary.ITEM_DELIMITER + itemDInfo);
        modelSolutions.add(itemBInfo + Summary.ITEM_DELIMITER + itemCInfo + Summary.ITEM_DELIMITER + itemEInfo);
        modelSolutions.add(itemAInfo + Summary.ITEM_DELIMITER + itemDInfo + Summary.ITEM_DELIMITER + itemEInfo);
        modelSolutions.add(itemBInfo + Summary.ITEM_DELIMITER + itemDInfo + Summary.ITEM_DELIMITER + itemEInfo);
        modelSolutions.add(itemCInfo + Summary.ITEM_DELIMITER + itemDInfo + Summary.ITEM_DELIMITER + itemEInfo);


        knapsack.setBoard(customBoard);
        knapsack.setBudget(budget); //Want maxCapacityUnits to be 5
        knapsack.addItem(itemAInfo, 0.02); //Want the costUnits to be 2
        knapsack.addItem(itemBInfo, 0.02);
        knapsack.addItem(itemCInfo, 0.02);
        knapsack.addItem(itemDInfo, 0.01);
        knapsack.addItem(itemEInfo, 0.01);

        knapsack.setNumItems(knapsack.getItems().size()); //Otherwise we get an exception thrown
        knapsack.setMaxCapacityUnits(knapsack.calcUnits(budget, false));

        try {
            Method m = knapsack.getClass().getDeclaredMethod(reconstructSolutionsMethodName);
            m.setAccessible(true);
            ArrayList<String> retrievedSolutions = (ArrayList<String>) m.invoke(knapsack);


            //Make asserting easier
            Comparator<String> comparator = new Comparator<String>() {
                @Override
                public int compare(String s, String t1) {
                    return s.compareTo(t1);
                }
            };
            retrievedSolutions.sort(comparator);
            modelSolutions.sort(comparator);

            //System.out.println(retrievedSolutions);
            //System.out.println(modelSolutions);

            assertTrue(retrievedSolutions.equals(modelSolutions));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
        }
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
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
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
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
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
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
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
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
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
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Check if all visited positions are marked correctly.
     */
    @Test
    public void markSolutionPositions() {
        double budget = 0.05;
        boolean[][] visitedBoardSolution = null;

        double[][] customBoard = new double[][]{
                {0,0,0,0,0,0},
                {0,0,1,1,1,1},
                {0,0,1,1,2,2},
                {0,0,1,1,2,2}
        };

        boolean[][] modelVisitedBoardSolution = new boolean[][]{
                {true,true,false,false,false,false},
                {true,false,true,true,false,false},
                {false,false,true,false,false,true},
                {false,false,false,false,false,true}
        };

        knapsack.setBoard(customBoard);
        knapsack.setBudget(budget); //Want maxCapacityUnits to be 5
        knapsack.addItem("A", 0.02); //Want the costUnits to be 2
        knapsack.addItem("B", 0.02);
        knapsack.addItem("C", 0.03); //Want the costUnits to be 3
        knapsack.setNumItems(knapsack.getItems().size()); //Otherwise we get an exception thrown
        knapsack.setMaxCapacityUnits(knapsack.calcUnits(budget, false));

        knapsack.setBoard(customBoard);

        try {
            Method m = knapsack.getClass().getDeclaredMethod("markSolutionPositions");
            m.setAccessible(true);
            ArrayList<Position> visitedPositionSolution = (ArrayList<Position>) m.invoke(knapsack);
            visitedBoardSolution = (boolean[][]) knapsack.getVisitedBoard();

            //Check the visited positions have been marked correctly
            assertEquals(visitedBoardSolution.length, modelVisitedBoardSolution.length);
            assertEquals(visitedBoardSolution[0].length, modelVisitedBoardSolution[0].length);
            for(int rowIndex = 0; rowIndex < visitedBoardSolution.length; rowIndex++) {
                assertArrayEquals(visitedBoardSolution[rowIndex], modelVisitedBoardSolution[rowIndex]);
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Check if all visited positions are marked correctly.
     */
    @Test
    public void markSolutionPositions2() {
        double budget = 0.05;
        boolean[][] visitedBoardSolution = null;

        double[][] customBoard = new double[][]{
                {0,0,0,0,0,0},
                {0,0,1,1,1,1},
                {0,0,1,1,2,2},
                {0,0,1,1,2,2}
        };

        boolean[][] modelVisitedBoardSolution = new boolean[][]{
                {false,true,false,false,false,false},
                {false,true,false,true,false,false},
                {false,false,false,true,false,true},
                {false,false,false,false,false,true}
        };

        knapsack.setBoard(customBoard);
        knapsack.setBudget(budget); //Want maxCapacityUnits to be 5
        knapsack.addItem("A", 0.02); //Want the costUnits to be 2
        knapsack.addItem("B", 0.02);
        knapsack.addItem("C", 0.02);
        knapsack.setNumItems(knapsack.getItems().size()); //Otherwise we get an exception thrown
        knapsack.setMaxCapacityUnits(knapsack.calcUnits(budget, false));

        knapsack.setBoard(customBoard);

        try {
            Method m = knapsack.getClass().getDeclaredMethod("markSolutionPositions");
            m.setAccessible(true);
            ArrayList<Position> visitedPositionSolution = (ArrayList<Position>) m.invoke(knapsack);
            visitedBoardSolution = (boolean[][]) knapsack.getVisitedBoard();

            //Check the visited positions have been marked correctly
            assertEquals(visitedBoardSolution.length, modelVisitedBoardSolution.length);
            assertEquals(visitedBoardSolution[0].length, modelVisitedBoardSolution[0].length);
            for(int rowIndex = 0; rowIndex < visitedBoardSolution.length; rowIndex++) {
                assertArrayEquals(visitedBoardSolution[rowIndex], modelVisitedBoardSolution[rowIndex]);
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
        }
    }


    /**
     * Check if all visited positions are marked correctly.
     */
    @Test
    public void markSolutionPositions3() {
        double budget = 0.05;
        boolean[][] visitedBoardSolution = null;

        double[][] customBoard = new double[][]{
                {0,0,0,0,0,0},
                {0,0,1,1,1,1},
                {0,0,1,1,2,2},
                {0,0,1,1,2,2},
                {0,1,1,2,2,3},
                {0,1,2,2,3,3}
        };

        boolean[][] modelVisitedBoardSolution = new boolean[][]{
                {true,true,false,false,false,false},
                {true,true,true,true,false,false},
                {false,true,true,true,true,false},
                {false,false,false,true,true,false},
                {false,false,false,false,true,true},
                {false,false,false,false,false,true}
        };

        knapsack.setBoard(customBoard);
        knapsack.setBudget(budget); //Want maxCapacityUnits to be 5
        knapsack.addItem("A", 0.02); //Want the costUnits to be 2
        knapsack.addItem("B", 0.02);
        knapsack.addItem("C", 0.02);
        knapsack.addItem("D", 0.01);
        knapsack.addItem("E", 0.01);
        knapsack.setNumItems(knapsack.getItems().size()); //Otherwise we get an exception thrown
        knapsack.setMaxCapacityUnits(knapsack.calcUnits(budget, false));

        knapsack.setBoard(customBoard);

        try {
            Method m = knapsack.getClass().getDeclaredMethod("markSolutionPositions");
            m.setAccessible(true);
            ArrayList<Position> visitedPositionSolution = (ArrayList<Position>) m.invoke(knapsack);
            visitedBoardSolution = (boolean[][]) knapsack.getVisitedBoard();

            //Check the visited positions have been marked correctly
            assertEquals(visitedBoardSolution.length, modelVisitedBoardSolution.length);
            assertEquals(visitedBoardSolution[0].length, modelVisitedBoardSolution[0].length);
            for(int rowIndex = 0; rowIndex < visitedBoardSolution.length; rowIndex++) {
                assertArrayEquals(visitedBoardSolution[rowIndex], modelVisitedBoardSolution[rowIndex]);
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Check if an ArrayList<Position> can be sorted correctly in increasing order. First by
     * itemIndex and then by capacityIndex.
     */
    @Test
    public void sortPositionArrayTest() {
        ArrayList<Position> modelVisitedPositions = new ArrayList<>(Arrays.asList(
                new Position(0,0),
                new Position(0,1),
                new Position(1,0),
                new Position(1,2),
                new Position(1,3),
                new Position(2,2),
                new Position(2,5),
                new Position(3,5)
        ));

        ArrayList<Position> param = new ArrayList<>(Arrays.asList(
                new Position(1,0),
                new Position(0,0),
                new Position(1,3),
                new Position(1,2),
                new Position(2,2),
                new Position(0,1),
                new Position(2,5),
                new Position(3,5)
        ));


        try {
            Method m = knapsack.getClass().getDeclaredMethod("sortPositionArray", ArrayList.class);
            m.setAccessible(true);
            ArrayList<Position> visitedPositionSolution = (ArrayList<Position>) m.invoke(knapsack, param);


            assertEquals(modelVisitedPositions, visitedPositionSolution);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Test if the index of items that are part of a solution can be correctly extracted.
     */
    @Test
    public void retrieveItemIndexesInSolutionTest() {
        ArrayList<Position> param = new ArrayList<>(Arrays.asList(new Position(2, 5),
                new Position(3, 5), new Position(1, 3), new Position(0, 1)));

        ArrayList<Integer> modelSolution = new ArrayList<>(Arrays.asList(1,2));

        try {
            Method m = knapsack.getClass().getDeclaredMethod("retrieveItemIndexesInSolution", ArrayList.class);
            m.setAccessible(true);
            ArrayList<Integer> indexSolutions = (ArrayList<Integer>) m.invoke(knapsack, param);

            //System.out.println(indexSolutions);
            //System.out.println(modelSolution);

            assertTrue(indexSolutions.equals(modelSolution));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
        }

    }

    /**
     * Test if the index of items that are part of a solution can be correctly extracted.
     */
    @Test
    public void retrieveItemIndexesInSolutionTest2() {
        ArrayList<Position> param = new ArrayList<>(Arrays.asList(new Position(5, 5),
                new Position(4, 5), new Position(3, 4), new Position(2, 4),
                new Position(1,2), new Position(0, 0)));

        ArrayList<Integer> modelSolution = new ArrayList<>(Arrays.asList(1,2, 4));

        try {
            Method m = knapsack.getClass().getDeclaredMethod("retrieveItemIndexesInSolution", ArrayList.class);
            m.setAccessible(true);
            ArrayList<Integer> indexSolutions = (ArrayList<Integer>) m.invoke(knapsack, param);

            assertTrue(indexSolutions.equals(modelSolution));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
        }

    }

    /**
     * Test if the index of items that are part of a solution can be correctly extracted. Should
     * return an empty list if the provided positions don't give any items for a solution.
     */
    @Test
    public void retrieveItemIndexesInSolutionTestNone() {
        ArrayList<Position> param = new ArrayList<>(Arrays.asList(new Position(0,0),
                new Position(1,0), new Position(2,0), new Position(3,0)));

        ArrayList<Integer> modelSolution = new ArrayList<>();

        try {
            Method m = knapsack.getClass().getDeclaredMethod("retrieveItemIndexesInSolution", ArrayList.class);
            m.setAccessible(true);
            ArrayList<Integer> indexSolutions = (ArrayList<Integer>) m.invoke(knapsack, param);

            assertTrue(indexSolutions.equals(modelSolution));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
        }

    }

    /**
     * Test if all the index can be correctly extracted if all positions are part o the solution.
     */
    @Test
    public void retrieveItemIndexesInSolutionAll() {
        ArrayList<Position> param = new ArrayList<>(Arrays.asList(new Position(0, 0),
                new Position(1, 1), new Position(2, 2), new Position(3, 3),
                new Position(4,4), new Position(5, 5)));

        ArrayList<Integer> modelSolution = new ArrayList<>(Arrays.asList(1,2,3,4,5));

        try {
            Method m = knapsack.getClass().getDeclaredMethod("retrieveItemIndexesInSolution", ArrayList.class);
            m.setAccessible(true);
            ArrayList<Integer> indexSolutions = (ArrayList<Integer>) m.invoke(knapsack, param);

            assertTrue(indexSolutions.equals(modelSolution));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail();
        }

    }

    /**
     * Custom assert that will solve the issue of the items being in the wrong order.
     */
    private void assertMaximizedListsEqual(String retrievedSolution, String modelSolutions) {
        ArrayList<String> retrievedLists = new ArrayList<>(Arrays.asList(Summary.separateMaximizedListSolutionss(retrievedSolution)));
        ArrayList<String> modelLists = new ArrayList<>(Arrays.asList(Summary.separateMaximizedListSolutionss(modelSolutions)));

        ArrayList<String> sortedRetrievedSolutions = new ArrayList<>();
        ArrayList<String> sortedModelLists = new ArrayList<>();

        for(String retrievedList : retrievedLists) {
            sortedRetrievedSolutions.add(sortItems(retrievedList));
        }

        for(String modelList : modelLists) {
            sortedModelLists.add(sortItems(modelList));
        }

        sortedModelLists.sort(comparator);
        sortedRetrievedSolutions.sort(comparator);

        System.out.println("Model solution: " + sortedModelLists);
        System.out.println("Retrieved solution: " + sortedRetrievedSolutions);

        assertEquals(sortedModelLists.size(), sortedRetrievedSolutions.size());
        for(int i = 0; i < sortedModelLists.size(); i++) {
            assertTrue(sortedModelLists.get(i).equals(sortedRetrievedSolutions.get(i)));
        }

    }

    /**
     * A String containing information of a list of items and returns a sorted summary.
     * @param listSummary List of items.
     * @return The same list of items but sorted.
     */
    private String sortItems(String listSummary) {
        ArrayList<String> items = new ArrayList<>(Arrays.asList(Summary.separateSummarizedList(listSummary)));
        items.sort(comparator);
        return Summary.summarizeStringArrayOfItems(items);
    }

}
