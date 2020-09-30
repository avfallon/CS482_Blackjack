package com.example.cs482_blackjack;

import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class BlackjackModel {

    public ArrayList<Integer> userCards = new ArrayList<Integer>();
    public ArrayList<Integer> dealerCards = new ArrayList<Integer>();
    public int userScore;
    public int dealerScore;
    public int[] cardDeck = fillDeck();

    public static final int NOTOVER = 0;
    public static final int USERWON = 1;
    public static final int DEALERWON = 2;
    public static final int TIE = 3;


    public BlackjackModel() {
    }

//    Function to get a random card image from the image folder and add it to the appropriate layout
    // Returns the ID of the dealt card
    public int getCard(boolean isDealer) {
        // This is the new card image's ID
        int randInt = new Random().nextInt(52);
        if (isDealer){
            for(int i=0;i<dealerCards.size();i++) {
                if (dealerCards.get(i) == randInt){
                    randInt = new Random().nextInt(52);
                }
            }
            for(int i=0;i<userCards.size();i++) {
                if (userCards.get(i) == randInt){
                    randInt = new Random().nextInt(52);
                }
            }
            dealerCards.add(randInt);
            dealerScore = calculateScore(dealerCards);
            Log.w("MA", "dealer score: " + dealerScore);
        }
        else{
            for(int i=0;i<userCards.size();i++) {
                if (userCards.get(i) == randInt){
                    randInt = new Random().nextInt(52);
                }
            }
            userCards.add(randInt);
            Log.w("MA", "userCards:"+userCards);
            userScore = calculateScore(userCards);
            Log.w("MA", "user score: " + userScore);

        }
        return cardDeck[randInt];
    }

    // returns the total score of an entire hand
    public int calculateScore(ArrayList<Integer> cards) {
        Log.w("MA", "cards:"+cards);
        int score = 0;
        boolean ace = false;
        for(int i=0;i<cards.size();i++) {
            int cardScore = cardPulledScore(cards.get(i));
            Log.w("MA", "card score: "+cardScore + cards);
            if(cardScore == 1) {
                ace = true;
            }
            score += cardScore;
            Log.w("MA", "myscore1"+score);
        }
        if(ace) {
            Log.w("MA", "Ace, score:"+score);
            if(score+10 <= 21) {
                score += 10;
            }
        }
        return score;
    }

    // Returns the score of a given card
    public int cardPulledScore(int randInt){
        if (randInt == 0 || randInt == 1 || randInt == 2 || randInt == 3){
            return 2;
        }
        else if (randInt == 4 || randInt == 5 || randInt == 6 || randInt == 7){
            return 3;
        }
        else if (randInt == 8 || randInt == 9 || randInt == 10 || randInt == 11){
            return 4;
        }
        else if (randInt == 12 || randInt == 13 || randInt == 14 || randInt == 15){
            return 5;
        }
        else if (randInt == 16 || randInt == 17 || randInt == 18 || randInt == 19){
            return 6;
        }
        else if (randInt == 20 || randInt == 21 || randInt == 22 || randInt == 23){
            return 7;
        }
        else if (randInt == 24 || randInt == 25 || randInt == 26 || randInt == 27){
            return 8;
        }
        else if (randInt == 28 || randInt == 29 || randInt == 30 || randInt == 31){
            return 9;
        }
        else if (randInt == 36 || randInt == 37 || randInt == 38 || randInt == 39){
            return 1;
        }
        else {
            return 10;
        }

    }

    public int[] fillDeck(){

        return new int[]{R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine, R.drawable.ten,
                R.drawable.eleven, R.drawable.twelve, R.drawable.thirteen, R.drawable.fourteen, R.drawable.fifteen, R.drawable.sixteen, R.drawable.seventeen, R.drawable.eighteen, R.drawable.nineteen, R.drawable.twenty, R.drawable.twenty_one, R.drawable.twenty_two,
                R.drawable.twenty_three, R.drawable.twenty_four, R.drawable.twenty_five, R.drawable.twenty_six, R.drawable.twenty_seven, R.drawable.twenty_eight, R.drawable.twenty_nine, R.drawable.thirty, R.drawable.thirty_one, R.drawable.thirty_two, R.drawable.thirty_three, R.drawable.thirty_four, R.drawable.thirty_five,
                R.drawable.thirty_six, R.drawable.thirty_seven, R.drawable.thirty_eight, R.drawable.thirty_nine, R.drawable.forty, R.drawable.forty_one, R.drawable.forty_two, R.drawable.forty_three, R.drawable.forty_four, R.drawable.forty_five, R.drawable.forty_six, R.drawable.forty_seven, R.drawable.forty_eight, R.drawable.forty_nine,
                R.drawable.fifty, R.drawable.fifty_one, R.drawable.fifty_two};

    }

    public void newGame() {
        userScore = 0;
        dealerScore = 0;
        userCards.clear();
        dealerCards.clear();
    }

    // Checks if the game is over, returns the result
    public int checkGame(boolean isDealer, int dealerHits) {
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
        else if(userScore > dealerScore && dealerHits == 2) {
            return USERWON;
        }

        return NOTOVER;
    }

    public boolean checkBlackjack(ArrayList<Integer> cards, int score) {
        if(cards.size() == 2 && score == 21) {
            return true;
        }
        return false;
    }
}
