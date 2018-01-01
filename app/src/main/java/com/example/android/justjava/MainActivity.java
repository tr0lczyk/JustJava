/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the increment button is clicked.
     * Adds one quantity to the volume of order
     */
    public void increment(View view) {
        if (quantity > 99){
            Toast.makeText(this, "You cannot order more than 99 coffees ;)", Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }
    /**
     * This method is called when the decrement button is clicked.
     * increment the order with 1 amount
     */
    public void decrement(View view) {
        if (quantity == 1){
            Toast.makeText(this,"You cannot order less than 1 coffee ;)", Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
    }

    /**
     * method called when order button is clicked
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.checkbox1_view);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);
        CheckBox choclateCheckBox = (CheckBox) findViewById(R.id.checkbox2_view);
        boolean hasChocolate = choclateCheckBox.isChecked();
        Log.v("MainActivity", "Adding Chocolate? " + hasChocolate);
        EditText  nameInput = (EditText) findViewById(R.id.editText_view);
        String takenName = nameInput.getText().toString();
        Log.v("MainActivity", "Client's name: " + nameInput);
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, takenName);
//        displayMessage(priceMessage);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for: " + takenName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @return the price of ordered coffees
     * @param cream is whether or not to add the price of cream
     * @param choco is wether or not the user needs chocolate
     */
    private int calculatePrice( boolean cream, boolean choco) {
        int basePrice = 5;
        if (cream == true){
            basePrice += 1;
        }

        if (choco == true){
            basePrice += 2;
        }
        return basePrice * quantity;
    }

    /**
     * Creates summary of the order.
     *
     * @return quantity and price of the order
     * @param additionalCream is stating if the customer would like to get the Whipped  cream
     * @param additionalChocolate is stating if the customr order chocolate or no
     */
    private String createOrderSummary(int priceOfOrder, boolean additionalCream, boolean additionalChocolate, String nameOfCustomer) {
        String priceMessage = "Name: Kapitan Kunal";
        priceMessage += "\nCustomer's name: " + nameOfCustomer;
        priceMessage += "\nAdd whipped cream? " + additionalCream;
        priceMessage += "\nAdd chocolate? " + additionalChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + priceOfOrder;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int book) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + book);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}