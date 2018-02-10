package com.example.patrick.shopper.Activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.example.patrick.shopper.CustomViews.ItemView;
import com.example.patrick.shopper.R;
import com.example.patrick.shopper.Utility.Summary;

import java.util.ArrayList;

/**
 * Activity is displayed when the user chooses to maximize their list. Will retrieve the summary of
 * items in the final list and display them.
 *
 * @author Patrick Ma
 */
public class MaximizedListActivity extends AppCompatActivity {

    private LinearLayout maximizedListView;
    private Context context = this;
    private ArrayList<ArrayList<ItemView>> maximizedItemLists = new ArrayList<>();
    private int currentMaximizedListIndex = 0;

    private ImageButton nextListImageBtn;
    private ImageButton prevListImageBtn;

    private int firstListIndex = 0;
    private int showNextListValue = 1;
    private int showPrevListValue = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maximized_list);

        findViews();
        setCustomActionBar();
        processMaximizedResults();
        displayItemViews(currentMaximizedListIndex);

        int numLists = maximizedItemLists.size();
        if(numLists <= 1) {
            //It is not possible to change lists since there are not multiple lists
            //Disable both previous and next list buttons
            nextListImageBtn.setClickable(false);
            prevListImageBtn.setClickable(false);
            nextListImageBtn.setImageResource(R.drawable.ic_right_arrow_disabled);
            prevListImageBtn.setImageResource(R.drawable.ic_left_arrow_disabled);
        } else {
            //There are multiple lists, can click next but can't click previous since by default
            //the first list is shown anyway
            nextListImageBtn.setClickable(true);
            nextListImageBtn.setImageResource(R.drawable.ic_right_arrow_enabled);
            prevListImageBtn.setClickable(false);
            prevListImageBtn.setImageResource(R.drawable.ic_left_arrow_disabled);
        }
    }

    /**
     * Take the result from the zero one knapsack, separate the maximized lists and for each list
     * create an ArrayList<ItemView> for the corresponding items. Store these arrays in
     * maximizedItemLists to be accessed throughout the class.
     */
    private void processMaximizedResults() {
        final String NO_SOLUTION = "";

        String maximizedListSummaries = getMaximizedLists();
        String[] maximizedListSummariesArray = Summary.separateMaximizedListSolutionss(maximizedListSummaries);

        for(String maximizedListSummary : maximizedListSummariesArray) {

            String[] items = Summary.separateSummarizedList(maximizedListSummary);

            //The array of items containing all items for this maximized list.
            ArrayList<ItemView> maximizedItems = new ArrayList<>();
            for (String itemInfo : items) {
                String itemName = Summary.extractName(itemInfo);
                double itemCost = Summary.extractCost(itemInfo);
                int itemQuantity = Summary.extractQuantity(itemInfo);

                ItemView itemView = new ItemView(context, itemName, itemCost, itemQuantity);
                itemView.hideRemoveButton();
                maximizedItems.add(itemView);
            }

            maximizedItemLists.add(maximizedItems);


        }
    }

    /**
     * Customize the ActionBar as well as add the navigational arrow to return to the previous
     * activity.
     */
    private void setCustomActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_secondary_actionbar);

    }

    /**
     * User has clicked the navigation arrow to return to the previous activity.
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    /**
     * Find views by ID and instantiate them.
     */
    private void findViews() {
        maximizedListView = findViewById(R.id.maximizedList);
        nextListImageBtn = findViewById(R.id.nextListImageBtn);
        prevListImageBtn = findViewById(R.id.prevListImageBtn);
    }


    /**
     * Add all the items to be displayed.
     *
     * @oaram maximizedListIndex The index for the list to be displayed from maximizedItemLists.
     */
    private void displayItemViews(int maximizedListIndex) {
        for (ItemView itemView : maximizedItemLists.get(maximizedListIndex)) {
            maximizedListView.addView(itemView);
        }
    }

    /**
     * Retrieve the item summary from the previous activity.
     * @return The item summary.
     */
    private String getMaximizedLists() {
        System.out.println(getIntent().getStringExtra(Intent.EXTRA_TEXT));
        return getIntent().getStringExtra(Intent.EXTRA_TEXT);
    }

    /**
     * Replace the current items being shown with the next list of items.
     * @param view
     */
    public void showNextList(View view) {
        updateListNavigationButtonClickability(showNextListValue);
        clearItems();
        displayItemViews(currentMaximizedListIndex);
    }

    /**
     * Replace the current items being shown with the previous list of items.
     * @param view
     */
    public void showPrevList(View view) {
        updateListNavigationButtonClickability(showPrevListValue);
        clearItems();
        displayItemViews(currentMaximizedListIndex);
    }

    /**
     * User has decided to move to different lists, disable and enable the required buttons.
     *
     * @param indexChange +1 to move to the next list, -1 to move to the prev list.
     */
    private void updateListNavigationButtonClickability(int indexChange) {
        int lastListIndex = maximizedItemLists.size() - 1;

        //See if it is possible to change the list index
        if(currentMaximizedListIndex == firstListIndex && indexChange == showPrevListValue) {
            //Do nothing, no previous list to display
        } else if (currentMaximizedListIndex == lastListIndex && indexChange == showNextListValue) {
            //do nothing, no next list to display
        } else {
            currentMaximizedListIndex += indexChange;
        }

        //Update the navigational button's interactability and image resources
        /*
        if(currentMaximizedListIndex == firstListIndex && currentMaximizedListIndex == lastListIndex) {
            prevListImageBtn.setClickable(false);
            prevListImageBtn.setImageResource(R.drawable.ic_left_arrow_disabled);
            nextListImageBtn.setClickable(false);
            nextListImageBtn.setImageResource(R.drawable.ic_right_arrow_disabled);
        } else*/ if(currentMaximizedListIndex == firstListIndex) {
            prevListImageBtn.setClickable(false);
            prevListImageBtn.setImageResource(R.drawable.ic_left_arrow_disabled);
            nextListImageBtn.setClickable(true);
            nextListImageBtn.setImageResource(R.drawable.ic_right_arrow_enabled);
        } else if (currentMaximizedListIndex == lastListIndex) {
            prevListImageBtn.setClickable(true);
            prevListImageBtn.setImageResource(R.drawable.ic_left_arrow_enabled);
            nextListImageBtn.setClickable(false);
            nextListImageBtn.setImageResource(R.drawable.ic_right_arrow_disabled);
        } else {
            prevListImageBtn.setClickable(true);
            prevListImageBtn.setImageResource(R.drawable.ic_left_arrow_enabled);
            nextListImageBtn.setClickable(true);
            nextListImageBtn.setImageResource(R.drawable.ic_right_arrow_enabled);
        }
    }


    /**
     * Clear the ItemView that are displayed.
     */
    private void clearItems() {
        maximizedListView.removeAllViews();
    }

    /**
     * Retruns the user to the activity where they are free to add/remove items to the list.
     * @param view The button pressed.
     */
    public void goBack(View view) {
        finish();
    }

}
