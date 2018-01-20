package com.example.patrick.shopper;

import com.example.patrick.shopper.Utility.ZeroOneKnapsack;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by patrick on 18/01/18.
 */

public class ZeroOneKnapsackUnitTest {

    private final Double BUDGET = 5.00;
    private ZeroOneKnapsack knapsack;
    private double  DEFAULT_VALUE = 1.0;


    @Before
    public void setUp() {
        knapsack = new ZeroOneKnapsack(BUDGET);
        knapsack.initKnapsack();
    }

    /**
     * Test the boundary if we get the expected result when we enter a budget of 0.
     */
    @Test
    public void calcUnitsZeroTest() {
        double amount = 0.0;

        assertEquals(0, knapsack.calcUnits(amount, true));
        assertEquals(0, knapsack.calcUnits(amount, false));
    }

    /**
     * Test if we get the correct result if we enter an amount that does not need rounding.
     */
    @Test
    public void calcUnitsNormalTest() {
        double amount = 5.50;

        assertEquals(55, knapsack.calcUnits(amount, true));
        assertEquals(55, knapsack.calcUnits(amount, false));

        double amount2 = 5.00;

        assertEquals(50, knapsack.calcUnits(amount2, true));
        assertEquals(50, knapsack.calcUnits(amount2, false));
    }

    /**
     * Test if we get the correct result if we enter an amount that needs rounding.
     */
    @Test
    public void calcUnitsRoundTest() {
        double amount = 5.55;

        assertEquals(56, knapsack.calcUnits(amount, true));
        assertEquals(55, knapsack.calcUnits(amount, false));

        double amount2 = 5.54;

        assertEquals(55, knapsack.calcUnits(amount2, true));
        assertEquals(55, knapsack.calcUnits(amount2, false));
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
        knapsack.addItem("A", 1.00);
        knapsack.addItem("B", 1.20);
        knapsack.addItem("C", 5.00);
        knapsack.addItem("D", 2.10);
        knapsack.addItem("E", 0.90);
        knapsack.addItem("F", 0.70);

        //System.out.println(knapsack.getItems());

        String modelSolution = "A\nB\nD\nF";

        String solution = knapsack.solve();

        //knapsack.displayBoard();

        //System.out.println(solution);

        assertTrue(modelSolution.equals(solution));



    }

}
