package com.example.cs482_blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.view.View;
import android.util.Log;
import android.widget.GridLayout.LayoutParams;

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
    public void dealCard(int layoutID ) {
        GridLayout layout = findViewById(layoutID);
        ImageView image = new ImageView(this);
        //This would be a call to the model
        image.setBackgroundResource(R.drawable.ten_of_hearts);
        layout.addView(image, 1);
        Log.w("MA", "Added image");
    }

    // Function accessed by 'hit' button in view, adds a card to the user's deck
    public void hit(View myBtn) {
        dealCard(R.id.userCards);
    }


    // Resets the view to create a new game
    public void newGame() {

    }

    // Deals two cards to the User and two cards to the Dealer, called at the start of the game
    public void initialDeal() {

    }


}