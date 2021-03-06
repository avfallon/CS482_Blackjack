package com.example.cs482_blackjack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.view.View;
import android.util.Log;
import android.widget.GridLayout.LayoutParams;
import android.widget.TextView;

import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Controller
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The ImageViews of all the cards for the user
     */
    public int[] userCardViews = {R.id.user0, R.id.user1, R.id.user2, R.id.user3, R.id.user4};
    /**
     * The ImageViews of all the cards in the dealers hand
     */
    public int[] dealerCardViews = {R.id.dealer0, R.id.dealer1, R.id.dealer2, R.id.dealer3, R.id.dealer4};
    /**
     * The amount of times the user has pressed hit
     */
    public int hitCount = 0;
    /**
     * Boolean to keep track if the dealer is the one playing
     */
    public boolean isDealer = false;
    /**
     * Boolean to see if the game is over
     */
    public boolean gameOver = false;
    /**
     * The game
     */
    public BlackjackModel model;

    // Possible game outcomes
    public static final int NOTOVER = 0;
    public static final int USERWON = 1;
    public static final int DEALERWON = 2;
    public static final int TIE = 3;


    /**
     * The onCreate function to start the application and what to run when the app is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new BlackjackModel();
        setContentView(R.layout.activity_main);
        initialDeal();    }

    /**
     * Function to get a random card image from the image folder and add it to the appropriate layout
     * @param layoutID
     */
    public void dealCard(int layoutID ) {
        // Gets the ID of a random card image
        int imageID = model.getCard(isDealer);

        ImageView imageView = findViewById(layoutID);
        imageView.setBackgroundResource(imageID);
    }

    /**
     * Function to hit and receive another card from the dealer
     * @param myButton the hit button
     */
    public void hit(View myButton) {
        if(hitCount < 3 && !gameOver) {
            dealCard(userCardViews[hitCount+2]);
            hitCount++;
            updateScore("user");

            /**
             * Checks if the game is over
             */
            int result = model.checkGame(isDealer);
            if(result > 0)  {
                endMessage(result);
                gameOver = true;
            }
            /**
             * Automatically ends the user's turn when they have hit 3 times or if they have 21
             */
            else if(hitCount == 3 || model.getScore("user") == 21) {
                stopGame(myButton);
            }
        }
    }

    /**
     * Change the Message to display the result of the game
     * @param resultFlag the result of the game
     */
    public void endMessage(int resultFlag) {
        TextView userLabel = (TextView) findViewById(R.id.user_msg);
        if(resultFlag == 1) {
            userLabel.setText("You Win!");
            userLabel.setTextColor(Color.RED);
        }
        else if(resultFlag == 2) {
            userLabel.setText("Dealer Wins");
            userLabel.setTextColor(Color.RED);
        }
        else if(resultFlag == 3) {
            userLabel.setText("Its a Tie");
            userLabel.setTextColor(Color.RED);
        }
    }

    /**
     * Resets the views to create a new game
     * @param myBtn new game button
     */
    public void newGame(View myBtn) {
        hitCount = 0;
        gameOver = false;
        isDealer = false;
        TextView userLabel = (TextView) findViewById(R.id.user_msg);
        userLabel.setTextColor(Color.WHITE);
        userLabel.setText("Hit or Stop?");
        findViewById(R.id.hitBtn).setClickable(true);

        /**
         * Removes all images from both hands
         */
        clearDeck(userCardViews);
        clearDeck(dealerCardViews);
        model.newGame();

        initialDeal();
    }

    /**
     * Removes all images from a given hand of cards
     * @param deck the deck of cards
     */
    public void clearDeck(int[] deck) {
        for(int i=0;i<deck.length;i++) {
            findViewById(deck[i]).setBackground(null);
        }
    }

    /**
     * Updates the given score label with
     * @param "user" or "dealer" based on which score needs updating
     */
    public void updateScore(String player) {
        int newScore = model.getScore(player);
        if(player.equals("user")) {
            ((TextView) findViewById(R.id.userScoreLabel)).setText("(Score = "+newScore+")");
        }
        else {
            ((TextView) findViewById(R.id.dealerScoreLabel)).setText("(Score = "+newScore+")");

        }
    }

    /**
     * Function when the user hits stop to pass the game on to the dealer
     * @param mybtn the stop button
     */
    public void stopGame(View mybtn){
        findViewById(R.id.hitBtn).setClickable(false);
        isDealer = true;
        dealersTurn();
    }

    /**
     * Function to start the game and initially deal two random cards to the user and the dealer
     */
    public void initialDeal() {
        /**
         * Adds two cards to the user's hand
         */
        dealCard(R.id.user0);
        dealCard(R.id.user1);
        updateScore("user");

        isDealer = true;
        /**
         * Adds two cards to the dealer's hand
         */
        dealCard(R.id.dealer0);
        dealCard(R.id.dealer1);
        updateScore("dealer");

        /**
         * Checks if either player got Blackjack
         */
        int gameStatus = model.checkInitialWin();
        if(gameStatus > 0)  {
            endMessage(gameStatus);
            gameOver = true;
        }
        isDealer = false;
    }

    /**
     * Function for the dealer to automatically play
     */
    public void dealersTurn(){
        int dealerHitCount = 0;
        /**
         * Checks if the dealer automatically wins without hitting
         */
        int gameStatus = model.checkGame(isDealer);
        if(gameStatus > NOTOVER) {
            endMessage(gameStatus);
            gameOver = true;
        }
        /**
         * Automatically hits until it wins, busts, or has hit 3 times
         */
        while(dealerHitCount < 3 && !gameOver) {
            dealCard(dealerCardViews[dealerHitCount + 2]);
            dealerHitCount++;
            updateScore("dealer");

            gameStatus = model.checkGame(isDealer);
            if(gameStatus > NOTOVER) {
                endMessage(gameStatus);
                gameOver = true;
            }
        }
        /**
         * Handles if the dealer hits 3 times and does not go over or beat the user
         */
        if(!gameOver) {
            endMessage(USERWON);
            gameOver = true;
        }
    }
}