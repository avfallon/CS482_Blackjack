//package com.example.cs482_blackjack;
//
//import android.util.Log;
//import android.widget.ImageView;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//public class BlackjackModel {
//
//    public int[] userCards = new int[5];
//    public int[] dealerCards = new int [5];
//    public boolean isDealer = false;
//    public int userScore;
//    public int dealerScore;
//
//
//    public BlackjackModel() {
//    }
//
////    Function to get a random card image from the image folder and add it to the appropriate layout
//    public void dealCard(int layoutID ) {
//        // This is the new card image's ID
//        int randInt = new Random().nextInt(52);
//        if (!isDealer){
//            int userScore = calculateScore(userCards);
//            userCards[](randInt);
//        }
//        else{
//            int dealerScore += cardScore;
//            dealerCards.add(randInt);
//        }
//        //GridLayout layout = findViewById(layoutID);
//        ImageView image = findViewById(layoutID);
//        //This would be a call to the model
//        int[] cardDeck = fillDeck();
//        image.setBackgroundResource(cardDeck[randInt]);
//        //layout.addView(image, 1);
//        Log.w("MA", "Added image");
//    }
//
//    public int calculateScore(int[] cards) {
//        int score = 0;
//        boolean ace = false;
//        for(int i=0;i<cards.size();i++) {
//            int cardScore = cardPulledScore(cards.get(i));
//            if(cardScore == 1) {
//                ace = true;
//            }
//            score += cardScore;
//        }
//        if(ace) {
//            if(score+10 <= 21) {
//                score += 10;
//            }
//        }
//        return score;
//    }
//
//    public int cardPulledScore(int randInt){
//        if (randInt == 0 || randInt == 1 || randInt == 2 || randInt == 3){
//            return 2;
//        }
//        else if (randInt == 4 || randInt == 5 || randInt == 6 || randInt == 7){
//            return 3;
//        }
//        else if (randInt == 8 || randInt == 9 || randInt == 10 || randInt == 11){
//            return 4;
//        }
//        else if (randInt == 12 || randInt == 13 || randInt == 14 || randInt == 15){
//            return 5;
//        }
//        else if (randInt == 16 || randInt == 17 || randInt == 18 || randInt == 19){
//            return 6;
//        }
//        else if (randInt == 20 || randInt == 21 || randInt == 22 || randInt == 23){
//            return 7;
//        }
//        else if (randInt == 24 || randInt == 25 || randInt == 26 || randInt == 27){
//            return 8;
//        }
//        else if (randInt == 28 || randInt == 29 || randInt == 30 || randInt == 31){
//            return 9;
//        }
//        else if (randInt == 36 || randInt == 37 || randInt == 38 || randInt == 39){
//            return 1;
//        }
//        else {
//            return 10;
//        }
//
//    }
//}
