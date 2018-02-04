package com.example.patrick.shopper.Activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import com.example.patrick.shopper.CustomViews.ItemView;
import com.example.patrick.shopper.R;
import com.example.patrick.shopper.Utility.Summary;
import java.util.Arrays;

/**
 * Activity is displayed when the user chooses to maximize their list. Will retrieve the summary of
 * items in the final list and display them.
 */
public class MaximizedListActivity extends AppCompatActivity {

    private LinearLayout maximizedList;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maximized_list);

        findViews();
        displayItemViews();
        setCustomActionBar();
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
        maximizedList = findViewById(R.id.maximizedList);
    }


    /**
     * Add all the items to be displayed.
     */
    private void displayItemViews() {
        String summarizedList = getItemSummary();

        System.out.println("Retrieved item summary to be added to the list: " + summarizedList);

        if(summarizedList.equals("")) {
            //Do nothing
        } else {

            String[] items = Summary.separateSummarizedList(summarizedList);

            System.out.println("Summary that was split: " + Arrays.deepToString(items));

            for (int itemIndex = 0; itemIndex < items.length; itemIndex++) {
                String[] data = Summary.separateItemInformation(items[itemIndex]);

                String name = data[Summary.ITEM_NAME_INDEX];
                Double cost = Double.parseDouble(data[Summary.ITEM_COST_INDEX]);
                int quantity = Integer.parseInt(data[Summary.ITEM_QUANTITY_INDEX]);

                ItemView item = new ItemView(context, name, cost, quantity);
                item.hideRemoveButton();

                maximizedList.addView(item);
            }
        }
    }

    /**
     * Retrieve the item summary from the previous activity.
     * @return The item summary.
     */
    private String getItemSummary() {
        return getIntent().getStringExtra(Intent.EXTRA_TEXT);
    }

    /**
     * Retruns the user to the activity where they are free to add/remove items to the list.
     * @param view The button pressed.
     */
    public void goBack(View view) {
        finish();
    }

}
