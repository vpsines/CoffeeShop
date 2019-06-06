package com.example.coffeeshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    int quantity=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        String priceMessage="Total= $";
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();
        int price=calculatePrice(hasWhippedCream,hasChocolate);
        EditText n=(EditText) findViewById(R.id.name_field);
        String name=n.getText().toString();
        createSummary(name,price,hasWhippedCream,hasChocolate);
    }
    public void createSummary(String name,int price,boolean addwhippedcream,boolean addChocolate){
        String Message="Name:"+name+"\nAdd whipped cream? "+addwhippedcream+"\nAdd chocolate? "+addChocolate+"\nQuantity:"+quantity+"\nPrice:$"+price+"\nThank you!";
        composeEmail(name,Message);
    }
    public int calculatePrice(boolean addWhippedCream,boolean addChocolate){
        int basePrice=5;
        if(addWhippedCream)
            basePrice+=1;
        if(addChocolate)
            basePrice+=2;
        return basePrice*quantity;
    }
    public void increment(View view) {
        if(quantity==100){
            Toast.makeText(this,"You cannot have more than 100 coffe",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity+1;
        display(quantity);
    }
    public void decrement(View view) {
        if(quantity==1){
            Toast.makeText(this,"You cannot order less than 1",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        display(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
    public void composeEmail(String name, String body) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        String subject="JustJava order for "+name;
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}