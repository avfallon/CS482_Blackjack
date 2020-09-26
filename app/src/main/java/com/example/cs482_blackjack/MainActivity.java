package com.example.cs482_blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGuiByCode();
    }

    public void buildGuiByCode() {
        setContentView(R.layout.activity_main);

    }

    //Function to get a random card image from the image folder and add it to the appropriate layout
}