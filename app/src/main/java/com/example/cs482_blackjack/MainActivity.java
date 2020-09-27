package com.example.cs482_blackjack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.view.View;
import android.util.Log;
import android.widget.GridLayout.LayoutParams;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public int hitCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGuiByCode();
        initialDeal();
    }

    public void buildGuiByCode() {
        setContentView(R.layout.activity_main);

    }

    public int[] fillDeck(){

        return new int[]{R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine, R.drawable.ten,
                R.drawable.eleven, R.drawable.twelve, R.drawable.thirteen, R.drawable.fourteen, R.drawable.fifteen, R.drawable.sixteen, R.drawable.seventeen, R.drawable.eighteen, R.drawable.nineteen, R.drawable.twenty, R.drawable.twenty_one, R.drawable.twenty_two,
                R.drawable.twenty_three, R.drawable.twenty_four, R.drawable.twenty_five, R.drawable.twenty_six, R.drawable.twenty_seven, R.drawable.twenty_eight, R.drawable.twenty_nine, R.drawable.thirty, R.drawable.thirty_one, R.drawable.thirty_two, R.drawable.thirty_three, R.drawable.thirty_four, R.drawable.thirty_five,
                R.drawable.thirty_six, R.drawable.thirty_seven, R.drawable.thirty_eight, R.drawable.thirty_nine, R.drawable.forty, R.drawable.forty_one, R.drawable.forty_two, R.drawable.forty_three, R.drawable.forty_four, R.drawable.forty_five, R.drawable.forty_six, R.drawable.forty_seven, R.drawable.forty_eight, R.drawable.forty_nine,
                R.drawable.fifty, R.drawable.fifty_one, R.drawable.fifty_two};

    }

    //Function to get a random card image from the image folder and add it to the appropriate layout
    public void dealCard(int layoutID ) {
        int randInt = new Random().nextInt(52);
        //GridLayout layout = findViewById(layoutID);
        ImageView image = findViewById(layoutID);
        //This would be a call to the model
        int[] cardDeck = fillDeck();
        image.setBackgroundResource(cardDeck[randInt]);
        //layout.addView(image, 1);
        Log.w("MA", "Added image");
    }

    // Function accessed by 'hit' button in view, adds a card to the user's deck
    public void hit(View myBtn) {
        if (hitCount < 3){
            if (hitCount == 0){
                dealCard(R.id.user2);
                hitCount++;
            }
            else if (hitCount == 1){
                dealCard(R.id.user3);
                hitCount++;
            }
            else {
                dealCard(R.id.user4);
                hitCount++;
            }
        }
    }


    // Resets the view to create a new game
    public void newGame() {

    }

    // Deals two cards to the User and two cards to the Dealer, called at the start of the game
    public void initialDeal() {
        ImageView userFirstCard = findViewById(R.id.user0);
        ImageView userSecondCard = findViewById(R.id.user1);

        int randInt1 = new Random().nextInt(52);
        int randInt2 = new Random().nextInt(52);

        int[] cardDeck = fillDeck();
        userFirstCard.setBackgroundResource(cardDeck[randInt1]);
        userSecondCard.setBackgroundResource(cardDeck[randInt2]);

    }


}