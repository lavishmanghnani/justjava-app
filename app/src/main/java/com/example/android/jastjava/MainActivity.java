
package com.example.android.jastjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            displayQuantity(quantity);
        } else {
            Toast.makeText(this, "You can't add out of hundred cups of coffee", Toast.LENGTH_SHORT).show();
        }
    }

    public void decrement(View view) {
        if (quantity >= 2) {
            quantity = quantity - 1;
            displayQuantity(quantity);
        } else {
            Toast.makeText(this, "You can't add less than one cup of coffee.", Toast.LENGTH_SHORT).show();
        }
    }


    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWithCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateBox = (CheckBox) findViewById((R.id.chocolate_checkbox));
        boolean hasChocolate = chocolateBox.isChecked();

        EditText name = (EditText) findViewById(R.id.name);
        String cName = name.getText().toString();

        int price = calculatePrice(hasWithCream, hasChocolate);
        String priceMeassage = createOrderSummary(price, hasWithCream, hasChocolate, cName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "jast java order for " + cName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMeassage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    public int calculatePrice(boolean haswithcream, boolean hasChocolate) {
        int basePrice = 5;
        if (haswithcream) {
            basePrice = basePrice + 1;
        }
        if (hasChocolate) {
            basePrice = basePrice + 2;
        }

        basePrice = basePrice * quantity;
        return basePrice;
    }

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String priceMessage = "Name : " + name;
        priceMessage += "\nAdd whipped ice cream ? " + addWhippedCream;
        priceMessage += "\nAdd chocolate ? " + addChocolate;
        priceMessage += "\nQuantity:" + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}
