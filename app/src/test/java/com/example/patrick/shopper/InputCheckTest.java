package com.example.patrick.shopper;

import com.example.patrick.shopper.Utility.InputCheck;
import org.junit.Test;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by patrick on 10/02/18.
 */

public class InputCheckTest {

    @Test
    public void nameTest() {
        assertTrue(InputCheck.name("pass"));
    }

    @Test
    public void nameTest2() {
        assertFalse(InputCheck.name(""));
    }

    @Test
    public void nameTest3() {
        assertFalse(InputCheck.name(null));
    }

    @Test
    public void costTest() {
        assertTrue(InputCheck.cost("0.01"));
    }

    @Test
    public void costTest2() {
        assertFalse(InputCheck.cost("0.00"));
    }

    @Test
    public void costTest3() {
        assertFalse(InputCheck.cost("-0.50"));
    }

    @Test
    public void costTest4() {
        assertTrue(InputCheck.cost("5"));
    }

    @Test
    public void quantityTest() {
        assertTrue(InputCheck.quantity("2"));
    }

    @Test
    public void quantityTest2() {
        assertTrue(InputCheck.quantity("1"));
    }

    @Test
    public void quantityTest3() {
        assertFalse(InputCheck.quantity("0"));
    }

    @Test
    public void quantityTest4() {
        assertFalse(InputCheck.quantity("-2"));
    }

}
