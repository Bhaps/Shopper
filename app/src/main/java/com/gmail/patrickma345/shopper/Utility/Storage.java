package com.gmail.patrickma345.shopper.Utility;

import android.content.Context;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Store the items added to the list in a String format using the Summary abstract class.
 * Also retrieve the String containing the summary of items that were added the last time the
 * ShoppingListActivity was running.
 *
 * The summary was created using the Summary abstract class.
 *
 * Created by patrickma345 on 30/01/18.
 * @author Patrick Ma
 */
public abstract class Storage {

    private static final String BUDGET_DATA_FILENAME = "budget.dat";
    private static final String ITEM_DATA_FILENAME = "listedItems.dat";


    /**
     * Retrieve the budget from persistant storage.
     * @param context
     * @return
     */
    public static double getBudget(Context context) {
        if(!fileExists(context, BUDGET_DATA_FILENAME)) {
            createFile(context, BUDGET_DATA_FILENAME);
        }

        StringBuilder stringBuilder = new StringBuilder();
        String readLine = "";

        try {
            FileInputStream fis = context.openFileInput(BUDGET_DATA_FILENAME);
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

        System.out.println("Retrieved budget: " + stringBuilder.toString());

        //Retrieve the budget as a String datatype.
        String budgetString = stringBuilder.toString();

        try {
            assert (budgetString.equals("") && Double.parseDouble(budgetString) >= 0.00) : new Error("Unexpected value saved");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if(budgetString == "") {
            return 0.00;
        } else {
            return Double.parseDouble(budgetString);
        }

    }

    /**
     * Save the budget to persistant storage.
     * @param budget The budget entered by the user.
     * @param context The app's context
     */
    public static void saveBudget(double budget, Context context) {
        System.out.println("Provided budget: " + budget);

        try {
            FileOutputStream fos = context.openFileOutput(BUDGET_DATA_FILENAME, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(outputStreamWriter);
            System.out.println("Will write to budget: " + new Double(budget).toString());
            bw.write(new Double(budget).toString());
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Retrieve the summary of previously stored items from the file stream.
     * @return Summary of previously stored items that was created using the Summary abstract class.
     */
    public static String getItemSummary(Context context) {
        //Check that the file exists in the first place, if not create it
        if(!fileExists(context, ITEM_DATA_FILENAME)) {
            createFile(context, ITEM_DATA_FILENAME);
        }


        StringBuilder stringBuilder = new StringBuilder();
        String readLine = "";

        try {
            FileInputStream fis = context.openFileInput(ITEM_DATA_FILENAME);
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
    }

    /**
     * Save the provided item summary to internal storage.
     * @param itemSummary
     */
    public static void saveItemSummary(String itemSummary, Context context) {

        System.out.println("Provided item summary: " + itemSummary);

        try {
            FileOutputStream fos = context.openFileOutput(ITEM_DATA_FILENAME, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(outputStreamWriter);
            bw.write(itemSummary);
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Clear the contents of the file by writing the empty string to the start of the file.
     */

    public static void clearFileContent(Context context, String filename) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
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
    private static void createFile(Context context, String filename) {
        // MODE_PRIVATE will create the file (or replace a file of the same name) and
        // make it private to your application
        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
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
    private static boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        return file.exists();
    }

    /**
     * Run this method to manually clear any data from the file.
     * @param args
     */
    public static void main(String[] args) {
        //clearFileContent();
        //saveItemSummary("A;1.0;1");

        System.out.println("aa".getBytes());
        System.out.println("\n".getBytes());
    }

}
