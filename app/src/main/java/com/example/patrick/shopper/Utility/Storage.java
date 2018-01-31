package com.example.patrick.shopper.Utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Store the items added to the list in a String format using the Summary abstract class.
 * Also retrieve the String containing the summary of items that were added the last time the
 * ShoppingListActivity was running.
 *
 * The summary was created using the Summary abstract class.
 *
 * Created by patrick on 30/01/18.
 */

public abstract class Storage {

    private static final String FILENAME = "listedItems.dat";
    //private static final int BUFFER_LENGTH = 10;


    /**
     * Create the file if it does not exist already and return a file stream where
     * we can read the summary of previously stored items.
     *
     * @return FileOutputStream of the file which stores the summary of previously listed items.
     */
    private static FileInputStream openFileInputStream() {
        File file = new File(FILENAME);
        FileInputStream fis = null;

        try {
            //If the file does not exist create it, no effect if it exists already
            file.createNewFile();

            fis = new FileInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fis;
    }

    /**
     * Create the file if it does not exist already and return a file stream where
     * we can write the summary of items currently listed.
     *
     * @return FileOutputStream of the file which stores the summary of previously listed items.
     */
    private static FileOutputStream openFileOutputStream() {
        File file = new File(FILENAME);
        FileOutputStream fos = null;

        try {
            //If the file does not exist create it, no effect if it exists already
            file.createNewFile();

            fos = new FileOutputStream(file, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fos;
    }

    /**
     * Close the provided stream.
     * @param stream
     */
    private static void closeFileStream(FileOutputStream stream) {
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the provided stream.
     * @param stream
     */
    private static void closeFileStream(FileInputStream stream) {
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve the summary of previously stored items from the file stream.
     * @return Summary of previously stored items that was created using the Summary abstract class.
     */
    public static String loadItems() {

        FileInputStream fos = openFileInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(fos);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();

        String readString = "";

        try {
            while((readString = bufferedReader.readLine()) != null) {
                stringBuilder.append(readString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //byte[] buffer = new byte[BUFFER_LENGTH];
        /*
        try {
            // fos.read(buffer) will return the total number of bytes read into the buffer, or -1 if
            // there is no more data because the end of the file has been reached.
            while(fos.read(buffer) != -1) {
                stringBuilder.append(new String(buffer));
                buffer = new byte[BUFFER_LENGTH];
            }

        } catch (IOException e) {
            e.printStackTrace();
        }*/




        closeFileStream(fos);

        String items = stringBuilder.toString();

        return items;
    }

    /**
     * Save the provided item summary to internal storage.
     * @param itemSummary
     */
    public static void saveItems(String itemSummary) {
        FileOutputStream fos = openFileOutputStream();

        try {
            fos.write(itemSummary.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeFileStream(fos);
    }


    /**
     * Clear the contents of the file by writing the empty string to the start of the file.
     */
    public static void clearFileContent() {
        FileOutputStream fos = openFileOutputStream();

        try {
            fos.write("".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeFileStream(fos);
    }

    public static void main(String[] args) {
        clearFileContent();

    }

}
