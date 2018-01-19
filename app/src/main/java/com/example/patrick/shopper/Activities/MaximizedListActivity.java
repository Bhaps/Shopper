package com.example.patrick.shopper.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.patrick.shopper.CustomViews.ItemView;
import com.example.patrick.shopper.R;
import com.example.patrick.shopper.Utility.Summary;

public class MaximizedListActivity extends AppCompatActivity {

    private LinearLayout maximizedList;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maximized_list);

        findViews();
        displayItemViews();
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

        if(summarizedList.equals("")) {
            //Do nothing
        } else {

            String[] items = Summary.separateSummarizedList(summarizedList);

            for (int itemIndex = 0; itemIndex < items.length; itemIndex++) {
                String[] data = Summary.separateItemInformation(items[itemIndex]);

                String name = data[0];
                Double price = Double.parseDouble(data[1]);
                int quantity = Integer.parseInt(data[2]);

                ItemView item = new ItemView(context, name, price, quantity);
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
