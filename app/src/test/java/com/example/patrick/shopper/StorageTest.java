package com.example.patrick.shopper;

import com.example.patrick.shopper.Utility.Storage;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by patrick on 31/01/18.
 */

public class StorageTest {

    /**
     * Test if a String can be saved and retrieved without error.
     */
    @Test
    public void saveAndRetrieveItemsTest() {
        String content = "Test content";
        Storage.saveItems(content);
        String retrievedContent = Storage.loadItems();
        assertTrue(content.equals(retrievedContent));
    }

    /**
     * Test if the contents of a file can be cleared.
     */
    @Test
    public void clearFileContentTest() {
        Storage.saveItems("Test message");
        Storage.clearFileContent();
        String content = Storage.loadItems();
        assertTrue(content.equals(""));
    }
}
