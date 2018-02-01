package com.example.patrick.shopper.Utility;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    /*
    private static FileInputStream openFileInputStream(Context context) {
        /*
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


    }*/

    /**
     * Create the file if it does not exist already and return a file stream where
     * we can write the summary of items currently listed.
     *
     * @return FileOutputStream of the file which stores the summary of previously listed items.
     */
    /*
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
    }*/

    /**
     * Close the provided stream.
     * @param stream
     */
    /*
    private static void closeFileStream(FileOutputStream stream) {
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * Close the provided stream.
     * @param stream
     */
    /*
    private static void closeFileStream(FileInputStream stream) {
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * Retrieve the summary of previously stored items from the file stream.
     * @return Summary of previously stored items that was created using the Summary abstract class.
     */
    public static String getItemSummary(Context context) {
        //Check that the file exists in the first place, if not create it
        if(!fileExists(context)) {
            createFile(context);
        }


        StringBuilder stringBuilder = new StringBuilder();
        String readLine = "";

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);


            // Read the entire contents from the file into the StringBuilder
            while((readLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(readLine);
            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Retrieved item summary: " + stringBuilder.toString());

        return stringBuilder.toString();


        /*
        FileInputStream fos = openFileInputStream(context);
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
        }*/


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



        /*
        closeFileStream(fos);

        String items = stringBuilder.toString();

        return items;*/
    }

    /**
     * Save the provided item summary to internal storage.
     * @param itemSummary
     */
    public static void saveItemSummary(String itemSummary, Context context) {
        /*
        FileOutputStream fos = openFileOutputStream();

        try {
            fos.write(itemSummary.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeFileStream(fos);*/

        System.out.println("Provided item summary: " + itemSummary);

        try {
            FileOutputStream fis = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fis.write(itemSummary.getBytes());
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Clear the contents of the file by writing the empty string to the start of the file.
     */

    public static void clearFileContent(Context context) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write("".getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Create an empty file.
     * @param context
     */
    private static void createFile(Context context) {
        // MODE_PRIVATE will create the file (or replace a file of the same name) and
        // make it private to your application
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write("".getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if the file exists.
     * @param context The context from the main activity.
     * @return True if the file exists, False if it doesn't.
     */
    private static boolean fileExists(Context context) {
        File file = context.getFileStreamPath(FILENAME);
        return file.exists();
    }

    /**
     * Run this method to manually clear any data from the file.
     * @param args
     */
    public static void main(String[] args) {
        //clearFileContent();
        //saveItemSummary("A;1.0;1");

        //System.out.println("aa".replaceAll("a$", ""));
    }

}
