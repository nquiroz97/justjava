//Created by Neri Quiroz on 7/3/2020

package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    //global variables
    int quantity = 0;
    int pricePerCup = 5;
    int priceOfWhipCream = 1;
    int priceOfChocolate = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //This method is called when the increment button is clicked.
    public void increment(View view) {
        if (quantity <= 99){
            quantity+=1;
        }
        else{
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        displayQuantity(quantity);

    }


    //This method is called when the decrement button is clicked.
    public void decrement(View view) {
        if (quantity >= 2){
            quantity-=1;
        }
        else{
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        displayQuantity(quantity);

    }

    //This method is called when the order button is clicked.
    public void submitOrder(View view) {
        //Get the customer name and store into a String variable
        EditText customerName = findViewById(R.id.customer_name);
        String nameOfCustomer = customerName.getText().toString();
        //Figure out if customer chooses whipped cream
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        //Figure out if customer chooses chocolate
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(nameOfCustomer, price, hasWhippedCream, hasChocolate);
        /*Creates an intent to compose an email containing the customer's order summary
           once the submit button is clicked */
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order summary for:" + nameOfCustomer);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
   private int calculatePrice(boolean whipCream, boolean chocolate) {
       if(whipCream && chocolate){
           return (quantity * pricePerCup) + ((quantity * priceOfWhipCream) + (quantity * priceOfChocolate)) ;
       }
       else if (whipCream){
           return (quantity * pricePerCup) + (quantity * priceOfWhipCream);
       }
       else if (chocolate){
           return (quantity * pricePerCup) + (quantity * priceOfChocolate);
       }
       else{
           return quantity * pricePerCup;
       }


   }

   private String createOrderSummary(String name,int price, boolean whippedCream, boolean chocolate){
       String priceMessage = getString(R.string.order_summary_name, name);
       priceMessage += getString(R.string.order_summary_whipped_cream, whippedCream);
       priceMessage += getString(R.string.order_summary_chocolate, chocolate);
       priceMessage += getString(R.string.order_summary_quantity, quantity);
       priceMessage += getString(R.string.order_summary_total, price);
       priceMessage += getString(R.string.order_summary_thank_you);
       return priceMessage;
   }

}