package com.example.patrick.shopper.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.patrick.shopper.CustomViews.ItemView;
import com.example.patrick.shopper.R;
import com.example.patrick.shopper.Utility.Summary;

import java.util.Locale;

public class ShoppingListActivity extends AppCompatActivity {

    //Views that need to be accessed throughout the class
    private LinearLayout itemList;
    private TextView totalCostTxtView;
    private TextView budgetTxtView;
    private Button maximizeBtn;

    private double totalCost;
    private double budget;
    private String lastEnteredName;
    private double lastEnteredPrice;
    private int lastEnteredQuantity;

    private Context context = this;

    private final Locale LOCALE = Locale.ENGLISH;

    //Custom views used to inflate dialog prompts.
    private View itemDetailsDialogView;
    private View budgetDialogView;

    //AlertDialog.show() are used to bring up prompts for the user to enter values
    private AlertDialog itemDetailsAlertDialog;
    private AlertDialog budgetAlertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        initComponents();
    }

    /**
     * Group all initialization methods together..
     */
    private void initComponents() {
        initValues();
        initViews();
        initializeDialogs();
        initList();

        //Check if the user can maximize their items at the very beginning.
        setMaximizeBtnInteractability();
    }

    /**
     * Initialize values for
     */
    private void initValues() {
        totalCost = 0.00;
        budget = 0.00;
    }

    /**
     * Set up the itemList with its listeners which responds to items being removed and
     * added to the list.
     */
    private void initList(){
        itemList.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                ItemView addedItem = (ItemView) child;
                increaseTotalCost(addedItem.getAllItemsPrice());
                updateTotalCost();
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {
                ItemView removedItem = (ItemView) child;
                decreaseTotalCost(removedItem.getAllItemsPrice());
                updateTotalCost();
            }
        });
    }

    /**
     * Update the total cost of all the listed items displayed and check if the maximizeBtn
     * should be enabled or disabled.
     */
    private void updateTotalCost() {
        totalCostTxtView.setText(String.format(LOCALE,"%.2f", totalCost));
        setMaximizeBtnInteractability();
    }

    /**
     * Update the budget displayed on the TextView and check if the maximizeBtn should be disabled
     * or enabled.
     */
    private void updateBudget() {
        budgetTxtView.setText(String.format(LOCALE, "%.2f", budget));
        setMaximizeBtnInteractability();
    }

    /**
     * Enable/disable optimization button button should only be pressed when the user has gone
     * over budget.
     */
    private void setMaximizeBtnInteractability() {
        if(totalCost > budget) {
            maximizeBtn.setEnabled(true);
        } else {
            maximizeBtn.setEnabled(false);
        }
    }


    /**
     * Item added to the list, increase the total price by the added item(s).
     */
    private void increaseTotalCost(double itemPrice) {
        totalCost += itemPrice;
    }

    /**
     * Item removed from the list, decrease the total price by the removed item(s).
     */
    private void decreaseTotalCost(double itemPrice) {
        totalCost -= itemPrice;
    }

    /**
     * Finds views by ID's and instantiates them to their respective private view variables.
     * Also add any necessary listeners.
     * */
    private void initViews() {

        itemList = findViewById(R.id.itemListLayout);
        totalCostTxtView = findViewById(R.id.totalCostTxtView);
        budgetTxtView = findViewById(R.id.budgetTxtView);
        maximizeBtn = findViewById(R.id.maximizeBtn);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        itemDetailsDialogView = layoutInflater.inflate(R.layout.prompt_item_details, null);
        budgetDialogView = layoutInflater.inflate(R.layout.prompt_budget_view, null);


        budgetTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                budgetAlertDialog.show();
            }
        });
    }

    /**Adds a new item to the list.*/
    public void addItemToList(ItemView item) {
        itemList.addView(item);
    }

    /**
     * Create an ItemView containing all the information of the represented item.
     * @return ItemView containing details of an item it represents.
     */
    private ItemView createItem() {
        ItemView newItem = new ItemView(context,
                lastEnteredName, lastEnteredPrice, lastEnteredQuantity);


        return newItem;
    }

    /**
     * Initialize the dialog prompt with a custom view.
     */
    private void initializeDialogs() {
        //Create the dialog for prompting the user to enter an item's details
        AlertDialog.Builder itemDetailsAlertDialogBuilder = new AlertDialog.Builder(context);
        itemDetailsAlertDialogBuilder.setView(itemDetailsDialogView);
        itemDetailsAlertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        })
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getEnteredItemValues();
                        ItemView newItem = createItem();
                        addItemToList(newItem);
                        dialogInterface.dismiss();
                    }
                });
        itemDetailsAlertDialog = itemDetailsAlertDialogBuilder.create();

        //Create the dialog for prompting the user to enter their budget
        AlertDialog.Builder budgetAlertDialogBuilder = new AlertDialog.Builder(context);
        budgetAlertDialogBuilder.setView(budgetDialogView);
        budgetAlertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        })
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getEnteredBudget();
                        updateBudget();
                    }
                });
        budgetAlertDialog = budgetAlertDialogBuilder.create();
    }


    /**
     * Show a dialog to prompt the user to enter the details for an item.
     * @param view
     */
    public void promptItemDetails(View view) {
        itemDetailsAlertDialog.show();
    }


    /**
     * Retrieve the values entered by the user from the EditText fields in the dialog.
     */

    private void getEnteredItemValues() {
        EditText nameEditTxt = itemDetailsDialogView.findViewById(R.id.enterName);
        EditText priceEditTxt = itemDetailsDialogView.findViewById(R.id.enterPrice);
        EditText quantityEditTxt = itemDetailsDialogView.findViewById(R.id.enterQuantity);

        lastEnteredName = nameEditTxt.getText().toString();
        lastEnteredPrice = Double.parseDouble(priceEditTxt.getText().toString());
        lastEnteredQuantity = Integer.parseInt(quantityEditTxt.getText().toString());
    }

    /**
     * Retrieve the values entered by the user for the budget in the dialog.
     */
    private void getEnteredBudget() {
        EditText budgetEditTxt = budgetDialogView.findViewById(R.id.budgetEditText);
        budget = Double.parseDouble(budgetEditTxt.getText().toString());

    }

    /**
     * Prepares the list to be send to a second activity. Also triggers the second activity
     * to start.
     *
     * The items added to the list must be represented as a String.
     * @param view The button that was pressed associated with this method.
     */
    public void maximizeList(View view) {
        String itemListSummary = Summary.summarizeList(itemList);

        Intent intent = new Intent(ShoppingListActivity.this, MaximizedListActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, itemListSummary);
        startActivity(intent);
    }
}