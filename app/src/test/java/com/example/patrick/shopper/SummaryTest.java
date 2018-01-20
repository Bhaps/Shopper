package com.example.patrick.shopper;

import android.widget.LinearLayout;

import com.example.patrick.shopper.Utility.Summary;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by patrick on 19/01/18.
 */

public class SummaryTest {

    /*
    @Test
    public void summarizeListAsStringTest() {
        LinearLayout layout = ;

    }*/


    /**
     * Test if cost of an item can be accurately extracted.
     */
    @Test
    public void extractCostTest() {
        String info = "A;1.00;1";

        double cost = Summary.extractCost(info);
        assertEquals(cost, 1.00, 0);

    }

    @Test
    public void separateSummarizedList() {
        String summary = "A" + Summary.INFO_DELIMITER + "1.00" + Summary.INFO_DELIMITER + "1"
                + Summary.ITEM_DELIMITER + "B" + Summary.INFO_DELIMITER + "2.00" + Summary.INFO_DELIMITER + "2";

        String[] result = Summary.separateSummarizedList(summary);

        assertEquals("A;1.00;1", result[0]);
        assertEquals("B;2.00;2", result[1]);
    }

}
