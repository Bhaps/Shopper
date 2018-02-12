package com.example.patrick.shopper;

import com.example.patrick.shopper.Utility.InputCheck;
import com.example.patrick.shopper.Utility.Summary;

import org.junit.Test;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

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
    public void nameTest4() {assertFalse(InputCheck.name(Summary.INFO_DELIMITER));
    }

    @Test
    public void nameTest5() {assertFalse(InputCheck.name(Summary.ITEM_DELIMITER));
    }

    @Test
    public void nameTest6() {assertFalse(InputCheck.name(Summary.LIST_DELIMITER));
    }

    @Test
    public void nameTest7() {assertFalse(InputCheck.name("Test" + Summary.INFO_DELIMITER + "Test"));
    }

    @Test
    public void nameTest8() {assertFalse(InputCheck.name("Test" + Summary.ITEM_DELIMITER + "Test"));
    }

    @Test
    public void nameTest9() {assertFalse(InputCheck.name("Test" + Summary.LIST_DELIMITER + "Test"));
    }

    @Test
    public void nameTest10() {
        System.out.println(InputCheck.name("\\"));
        assertFalse(InputCheck.name("\\"));
        fail();
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
    public void costTest5() {
        assertFalse(InputCheck.cost(null));
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

    @Test
    public void quantityTest5() {
        assertFalse(InputCheck.quantity(null));
    }

    @Test
    public void quantityTest6() {
        assertFalse(InputCheck.quantity("Not a number"));
    }

    @Test
    public void passesBlacklistedCharCheckTest() {assertTrue(InputCheck.passesBlacklistedCharCheck(""));}

    @Test
    public void passesBlacklistedCharCheckTest2() {assertFalse(InputCheck.passesBlacklistedCharCheck(null));}

    @Test
    public void passesBlacklistedCharCheckTest3() {assertTrue(InputCheck.passesBlacklistedCharCheck("Not blacklisted."));}

    @Test
    public void passesBlacklistedCharCheckTest4() {assertFalse(InputCheck.passesBlacklistedCharCheck(Summary.INFO_DELIMITER));}

    @Test
    public void passesBlacklistedCharCheckTest5() {assertFalse(InputCheck.passesBlacklistedCharCheck(Summary.LIST_DELIMITER));}

    @Test
    public void passesBlacklistedCharCheckTest6() {assertFalse(InputCheck.passesBlacklistedCharCheck(Summary.ITEM_DELIMITER));}

    @Test
    public void passesBlacklistedCharCheckTest7() {assertFalse(InputCheck.passesBlacklistedCharCheck("Test" + Summary.ITEM_DELIMITER + "Test"));}

    @Test
    public void passesBlacklistedCharCheckTest8() {assertFalse(InputCheck.passesBlacklistedCharCheck("Test" + Summary.INFO_DELIMITER + "Test"));}

    @Test
    public void passesBlacklistedCharCheckTest9() {assertFalse(InputCheck.passesBlacklistedCharCheck("Test" + Summary.LIST_DELIMITER + "Test"));}
}
