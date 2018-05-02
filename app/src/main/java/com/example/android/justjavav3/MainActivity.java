package com.example.android.justjavav3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    boolean addWhippedCream = false;
    boolean addChocolate = false;
    String name = "";
    int duration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText editTextName = (EditText) findViewById(R.id.name_field);
        name = editTextName.getText().toString();

        CheckBox checkBoxWhippedCream = (CheckBox) findViewById(R.id.checkbox_whipped_cream);
        addWhippedCream = checkBoxWhippedCream.isChecked();

        CheckBox checkBoxChocolate = (CheckBox) findViewById(R.id.checkbox_chocolate);
        addChocolate = checkBoxChocolate.isChecked();

        int price = calculatePrice();
        String message = createOrderSummary(price);

        String[] toAddresses =  new String[1];
        toAddresses[0] = "mikedeverell@gmail.com";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, toAddresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "my first intent - coffee order");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            displayQuantity(quantity);
        } else {
            Toast.makeText(getApplicationContext(), "You cannot order more than 100 coffees", duration).show();
        }
    }

    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            displayQuantity(quantity);
        } else {
            Toast.makeText(getApplicationContext(), "You cannot order less than 1 coffee", duration).show();
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberQuantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberQuantity);
    }

    private int calculatePrice() {
        int total = 5;
        if (addWhippedCream) {
            total += 1;
        }
        if (addChocolate) {
            total += 2;
        }
        return total * quantity;
    }

    private String createOrderSummary(int price) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.cream_option, addWhippedCream);
        priceMessage += "\n" + getString(R.string.cholocate_option, addChocolate);
        priceMessage += "\n" + getString(R.string.quantity_title, quantity);
        priceMessage += "\n" + getString(R.string.total, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
}
