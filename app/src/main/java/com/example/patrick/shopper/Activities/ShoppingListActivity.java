package com.example.patrick.shopper.Activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.patrick.shopper.CustomViews.ItemView;
import com.example.patrick.shopper.R;
import com.example.patrick.shopper.Threads.StartMaximizedListActivity;
import com.example.patrick.shopper.Threads.MaximizeItemsCallable;
import com.example.patrick.shopper.Threads.ThreadCompleteListener;
import com.example.patrick.shopper.Utility.Storage;
import com.example.patrick.shopper.Utility.Summary;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * The main screen that is shown when the application is started. Allows the user to add and remove
 * items into the shopping list with the item's details (name, price and quantity). The user is able
 * to enter a budget. If the total cost of the items are greater than the budget, the user can
 * press a button to maximize the number of items while being under budget; this maximized list is
 * displayed in the MaximizedListActivity.
 */
public class ShoppingListActivity extends AppCompatActivity implements ThreadCompleteListener {

    //Views that need to be accessed throughout the class
    private LinearLayout itemList;
    private TextView totalCostTxtView;
    private TextView budgetTxtView;
    private TextView messageTxtView; //Used to display a message in a dialog
    private ImageButton maximizeBtn;

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
    private View progressDialogView;
    private View messageDialogView;

    //AlertDialog.show() are used to bring up prompts for the user to enter values
    private AlertDialog itemDetailsAlertDialog;
    private AlertDialog budgetAlertDialog;
    private AlertDialog progressAlertDialog;
    private AlertDialog messageAlertDialog;


    private Future<String> futureCall;
    private ExecutorService executorService;
    private MaximizeItemsCallable maximizeItemsCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        setCustomActionBar();
        initComponents();
        loadItems();
    }

    /**
     * When your activity is no longer visible to the user, it has entered the Stopped state,
     * and the system invokes the onStop() callback. This may occur, for example, when a newly
     * launched activity covers the entire screen. The system may also call onStop() when the
     * activity has finished running, and is about to be terminated.
     */
    @Override
    public void onStop() {
        super.onStop();
        saveListedItems();
    }

    /**
     * Summarize the items currently added to the list and then store the summary.
     */
    private void saveListedItems() {
        String itemSummary = Summary.summarizeListAsString(itemList);
        Storage.saveItemSummary(itemSummary, context);

        System.out.println("Will save the items: " + itemSummary);
    }

    /**
     * Load the previously added items.
     */
    private void loadItems() {
        //Storage.clearFileContent(context); //Uncomment to manually clear the contents

        //Get the summary of items that were previously added
        String previousItemSummary = Storage.getItemSummary(context);

        System.out.println("Retrieved item summary!!!!!!!: " + previousItemSummary);

        if(previousItemSummary.equals("")) {
            //Do nothing, there were no previous items added before.
        } else {
            String[] previousItems = Summary.separateSummarizedList(previousItemSummary);

            System.out.println("After the summary is separated: " + Arrays.deepToString(previousItems));

            for (String itemInfo : previousItems) {
                String name = Summary.extractName(itemInfo);
                double price = Summary.extractCost(itemInfo);
                int quantity = Summary.extractQuantity(itemInfo);

                addItemToList(createItem(name, price, quantity));
            }
        }
    }

    /**
     * Personalize the ActionBar so centre it.
     */
    private void setCustomActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_main_actionbar);
    }

    /**
     * Group all initialization methods together..
     */
    private void initComponents() {
        initValues();
        initViews();
        initDialogs();
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
        budgetTxtView = findViewById(R.id.displayBudgetTxtView);
        maximizeBtn = findViewById(R.id.maximizeImgBtn);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        itemDetailsDialogView = layoutInflater.inflate(R.layout.prompt_item_details, null);
        budgetDialogView = layoutInflater.inflate(R.layout.prompt_budget_view, null);
        progressDialogView = layoutInflater.inflate(R.layout.loading_view, null);
        messageDialogView = layoutInflater.inflate(R.layout.message_view, null);


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
    private ItemView createItem(String name, double price, int quantity) {
        ItemView newItem = new ItemView(context, name, price, quantity);


        return newItem;
    }

    /**
     * Initialize the dialog prompt with a custom view.
     */
    private void initDialogs() {
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
                        ItemView newItem = createItem(lastEnteredName, lastEnteredPrice, lastEnteredQuantity);
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
                        budget = getEnteredBudget();
                        updateBudget();
                    }
                });
        budgetAlertDialog = budgetAlertDialogBuilder.create();

        //Create the dialog for displaying the progress bar when the user has selected the
        //option to maximize their shopping list
        AlertDialog.Builder progressAlertDialogBuilder = new AlertDialog.Builder(context);
        progressAlertDialogBuilder.setView(progressDialogView);
        progressAlertDialog = progressAlertDialogBuilder.create();
        progressAlertDialog.setCanceledOnTouchOutside(false);

        //Create the dialog for displaying a message.
        AlertDialog.Builder messageAlertDialogBuilder = new AlertDialog.Builder(context);
        messageAlertDialogBuilder.setView(messageDialogView);
        messageAlertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        messageAlertDialog = messageAlertDialogBuilder.create();
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
    private double getEnteredBudget() throws NumberFormatException {
        EditText budgetEditTxt = budgetDialogView.findViewById(R.id.budgetEditText);
        //budget = Double.parseDouble(budgetEditTxt.getText().toString());
        return Double.parseDouble(budgetEditTxt.getText().toString());
    }

    /**
     * Show the dialog which displays a message and the progress indicator.
     */
    private void showProgressDialog() {
        progressAlertDialog.show();
    }

    /**
     * User has clicked the maximize shopping list button. Will check if conditions are met to
     * begin maximizing their list (the valid budget is entered). Will then create the threads
     * which will run the algorithm to maximize the list.
     *
     * @param view The button that was pressed associated with this method.
     */
    public void startMaximizingList(View view) {
        //First check that a budget has been entered.
        try {
            double enteredBudget = getEnteredBudget();

            if(enteredBudget <= 0.0) {
                throw new NumberFormatException("The budget cannot be zero or negative.");
            }

            showProgressDialog();

            maximizeItemsCall = new MaximizeItemsCallable(budget, itemList);
            executorService = Executors.newSingleThreadExecutor();

            maximizeItemsCall.addListener(this);

            futureCall = executorService.submit(maximizeItemsCall);
        } catch (NumberFormatException e){
            showMessageDialog("You did not enter a valid budget.");
        }
    }

    /**
     * Display a dialog which just has shows the provided message.
     * @param message The message to be shown to the user.
     */
    private void showMessageDialog(String message) {
        messageTxtView = messageDialogView.findViewById(R.id.test);
        messageTxtView.setText(message);
        messageAlertDialog.show();

    }

    /**
     * Has finished maximizing the items, need to close the progress dialog and move to the
     * next activity.
     * @param call
     */
    @Override
    public void notifyOfThreadComplete(Callable call) {
        System.out.println("THE ACITVITY WAS NOTIFIED THAT THE THREAD HAS FINISHED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        progressAlertDialog.dismiss();

        Intent intent = new Intent(ShoppingListActivity.this, MaximizedListActivity.class);
        executorService.submit(new StartMaximizedListActivity(futureCall, intent, context));
    }
}