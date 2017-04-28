package com.example.android.tipcalc2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable; // Used with EditText
import android.text.TextWatcher; // Listener for EditText
import android.widget.EditText; // EditText widget
import android.widget.SeekBar; // SeekBar widget
import android.widget.SeekBar.OnSeekBarChangeListener; //Listening for changes with SeekBar
import android.widget.TextView; // For displaying text

import java.text.NumberFormat; // This is used for currency formatting

/**
 * This is from the book Android 6 for programmers. By Deitel and Deitel. T
 */

public class MainActivity extends AppCompatActivity {

    //To format currency and percent
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private double billAmount = 0.0; // The initial amount
    private double percent = 0.15; // Initial percentage that can be changed
    private TextView amountTextView; // The bill amount
    private TextView percentTextView; // The tip selected
    private TextView tipTextView; // How much the tip is
    private TextView totalTextView; // Amount due

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Call the superclass version
        setContentView(R.layout.activity_main); // Call the layout and inflate

        // Get references to all the views for the Activity
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);

        // Update the GUI
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        // Method for Seekbar listener
        SeekBar percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);

    }

    //calculate and display tip and total amounts
    private void calculate() {
        //format percent and display in percentTextView
        percentTextView.setText(percentFormat.format(percent));

        //calculate the tip and total
        double tip = billAmount * percent;
        double total = billAmount + tip;

        //display tip and total formatted as currency
        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));

    }

    //listener object for the SeekBar's progress for when the user interacts with it
    private final OnSeekBarChangeListener seekBarListener =
            new OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    percent = progress; //100.0
                    calculate();// calculate and display tip and total

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };

    //listener object for the EditText's text changed events
    private final TextWatcher amountEditTextWatcher =
            new TextWatcher() {
                //called when the user changes the bill amount
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try { //get bill amount and display currency formatted value
                        billAmount = Double.parseDouble(s.toString()) / 100.0;
                        amountTextView.setText(currencyFormat.format(billAmount));
                    } catch (NumberFormatException e) { // if s is empty or non-numeric
                        amountTextView.setText("");
                        billAmount = 0.0;
                    }

                    calculate(); // update the tip and total TextViews
                }

                @Override
                public void afterTextChanged(Editable s) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

                }
            };


}

//TODO(1) The ux looks all wrong. Get the three textviews in line with each other.
//TODO(2) The boxes are not on top of each other. Fix that.
//TODO(3) The seekbar breaks when the amount is anything but 15%.
//TODO(4) The input only supports int.