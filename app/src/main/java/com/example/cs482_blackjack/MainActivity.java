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
import android.widget.TextView;

import java.util.Random;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public int[] userCardViews = {R.id.user0, R.id.user1, R.id.user2, R.id.user3, R.id.user4};
    public int[] dealerCardViews = {R.id.dealer0, R.id.dealer1, R.id.dealer2, R.id.dealer3, R.id.dealer4};
    public int hitCount = 0;
    public int dealerHitCount = 0;
    public int userScore = 0;
    public int dealerScore = 0;
    public ArrayList<Integer> userCards = new ArrayList<Integer>();
    public ArrayList<Integer> dealerCards = new ArrayList<Integer>();
    public boolean isDealer = false;
    public boolean gameOver = false;
    public BlackjackModel model;

    public static final int NOTOVER = 0;
    public static final int USERWON = 1;
    public static final int DEALERWON = 2;
    public static final int TIE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGuiByCode();
    }

    public void buildGuiByCode() {
        model = new BlackjackModel();
        setContentView(R.layout.activity_main);
        initialDeal();
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
//        int cardScore = cardPulledScore(randInt);
        int cardScore = 0;
        if (!isDealer){
            userScore += cardScore;
            userCards.add(randInt);
        }
        else{
            dealerScore += cardScore;
            dealerCards.add(randInt);
        }
        //GridLayout layout = findViewById(layoutID);
        ImageView image = findViewById(layoutID);
        //This would be a call to the model
        int[] cardDeck = fillDeck();
        image.setBackgroundResource(cardDeck[randInt]);
        //layout.addView(image, 1);
    }

    public void hit(View myButton) {
        if(hitCount < 3 && !gameOver) {
            int image = model.dealCard(userCardViews[hitCount+2]);
            ImageView view = (ImageView) findViewById(userCardViews[hitCount+2]);
            image.setBackgroundResource

            hitCount++;

            // Checks if the game is over
            int result = checkGame();
            Log.w("MA", "result: "+result);
            if(result > 0)  {
                endMessage(result);
            }
        }
    }

    public void endMessage(int resultFlag) {
        TextView userLabel = (TextView) findViewById(R.id.user_msg);
        if(resultFlag == 1) {
            userLabel.setText("You Win!");
        }
        else if(resultFlag == 2)
            userLabel.setText("You Lost!");
        else if(resultFlag == 3) {
            userLabel.setText("Its a Tie!");
        }
    }

    public int checkGame() {
        gameOver = true;
        if(checkBlackjack(dealerCards, dealerScore)) {
            if(checkBlackjack(userCards, userScore)) {
                return TIE;
            }
            return DEALERWON;
        }
        else if(checkBlackjack(userCards, userScore)) {
            return USERWON;
        }
        else if(dealerScore == 21) {
            if(userScore == 21) {
                return TIE;
            }
            return DEALERWON;
        }
        else if(userScore == 21) {
            return USERWON;
        }
        else if(userScore > 21) {
            return DEALERWON;
        }
        else if(dealerScore > 21 && userScore < 21) {
            return USERWON;
        }
        else if(isDealer && dealerScore > userScore) {
            return DEALERWON;
        }
        else if(dealerHitCount == 3 && userScore > dealerScore) {
            return USERWON;
        }
        gameOver = false;
        return NOTOVER;

    }

    public boolean checkBlackjack(ArrayList<Integer> cards, int score) {
        if(cards.size() == 2 && score == 21) {
            return true;
        }
        return false;
    }

    // Resets the view to create a new game
    public void newGame(View myBtn) {
        hitCount = 0;
        userScore = 0;
        dealerScore = 0;
        userCards.clear();
        gameOver = false;

        // Removes all images from both hands
        clearDeck(userCardViews);
        clearDeck(dealerCardViews);

        initialDeal();
    }

    // Removes all images from a given hand of cards
    public void clearDeck(int[] deck) {
        for(int i=0;i<deck.length;i++) {
            findViewById(deck[i]).setBackground(null);
        }
    }

    public void stopGame(View mybtn){
        findViewById(R.id.hitBtn).setClickable(false);
        isDealer = true;
        dealersTurn();
    }

    public void initialDeal() {
        userScore = dealTwo(R.id.user0, R.id.user1, userCards);
        dealerScore = dealTwo(R.id.dealer0, R.id.dealer1, dealerCards);
    }

    public int dealTwo(int cardID_1, int cardID_2, ArrayList<Integer> cardList) {
        ImageView cardOne = findViewById(cardID_1);
        ImageView cardTwo = findViewById(cardID_2);

        // Gets the ID of the card images
        int randInt1 = new Random().nextInt(52);
        int randInt2 = new Random().nextInt(52);

        //adds the images to the empty views
        cardList.add(randInt1);
        cardList.add(randInt2);

        int score = 0;
        int firstScore = cardPulledScore(randInt1);
        score += firstScore;
        int secondScore = cardPulledScore(randInt2);
        score += secondScore;

        int[] cardDeck = fillDeck();
        cardOne.setBackgroundResource(cardDeck[randInt1]);
        cardTwo.setBackgroundResource(cardDeck[randInt2]);

        Log.w("MA", "inside: "+cardList);
        return score;
    }

    public void dealersTurn(){
        if (userScore > 21){
            return;
        }
        else if (dealerScore == 21){
            if (userScore == 21){
                System.out.println("Tie");
            }
        }
        while (dealerScore < 17 || dealerScore <= userScore){
            findViewById(R.id.hitBtn).performClick();
        }
    }
}