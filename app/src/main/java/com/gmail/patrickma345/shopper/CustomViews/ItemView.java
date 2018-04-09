package com.gmail.patrickma345.shopper.CustomViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmail.patrickma345.shopper.R;

/**
 * Custom view which contains information of the name, price and quantity of the item.
 * Also contains functionality to be removed and added to a list.
 *
 * //TODO update to say it can be whitelisted and what it means
 *
 * Created by patrickma345 on 29/12/17.
 */

public class ItemView extends LinearLayout {

    private String name;
    private double price;
    private int quantity;

    private TextView nameTxtView;
    private TextView priceTxtView;
    private TextView quantityTxtView;
    private View customItemView;
    private ImageButton removeBtn;



    public ItemView(Context context) {
        super(context);
        initializeViews(context);
        findViews();
    }

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
        findViews();
    }

    public ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
        findViews();
    }

    /**
     * Inflates the views in the layout.
     * @param context The current context for the view
     */
    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customItemView = inflater.inflate(R.layout.item_view, this);
    }

    /**
     * @return The custom view that was infalted.
     */
    public View getCustomItemView() {
        return customItemView;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    /**
     * Initializer used to set the values for the item's attributes and update the
     * TextViews to display the attributes.
     * @param context
     * @param name
     * @param price
     * @param quantity
     */
    public ItemView(Context context, String name, double price, int quantity) {
        super(context);
        this.name = name;
        this.price = price;
        this.quantity = quantity;

        initializeViews(context);
        findViews();
        updateDisplay();
    }

    /**
     * Initialize the declared views by finding them using their ID's.
     */
    private void findViews() {
        nameTxtView = findViewById(R.id.nameTxtView);
        priceTxtView = findViewById(R.id.priceTxtView);
        quantityTxtView = findViewById(R.id.quantityTxtView);
        removeBtn = findViewById(R.id.removeBtn);

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem();
            }
        });

    }

    /**
     * Called when the ItemView's values have changed and want to set then update the new values
     * to be displayed.
     * @param name The new name.
     * @param price The new price.
     * @param quantity The new quantity.
     */
    public void edit(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;

        updateDisplay();
    }

    /**
     * Updates the texts displayed by the TextViews on the custom view.
     */
    public void updateDisplay() {
        nameTxtView.setText(name);
        priceTxtView.setText(String.format("$%.2f", price));
        quantityTxtView.setText(String.format("%d", quantity));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    /**
     * @return Returns the price of all the items.
     */
    public double getAllItemsPrice() {
        return price * quantity;
    }

    /**
     * @return The price of a single item.
     */
    public double getSinglePrice() {return price;}

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\nPrice: " + Double.toString(this.price) + "\nQuantity"
                + Integer.toString(this.quantity);
    }

    /**
     * Remove the item from the list.
     */
    public void removeItem() {
        //System.out.println("Clicked the remove button.");
        ((LinearLayout) this.getParent()).removeView(this);
    }


    /**
     * Use when the remove button is not wanted to be shown.
     */
    public void hideRemoveButton() {
        removeBtn.setVisibility(View.GONE);
    }
}
