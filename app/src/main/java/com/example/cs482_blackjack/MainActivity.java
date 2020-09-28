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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public int hitCount = 0;
    public int dealerHitCount = 0;
    public int userScore = 0;
    public int dealerScore = 0;
    public ArrayList<Integer> userCards = new ArrayList<Integer>();
    public ArrayList<Integer> dealerCards = new ArrayList<Integer>();
    public boolean isDealer = false;

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
        int cardScore = cardPulledScore(randInt, userScore);
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
        Log.w("MA", "Added image");
    }

    // Function accessed by 'hit' button in view, adds a card to the user's deck
    public void hit(View myBtn) {
        if (!isDealer){
            if (userScore == 21){
                System.out.println("You won");
                return;
            }
            else if (userScore > 21){
                System.out.println("Busted");
                return;
            }
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
            System.out.println(userScore);
            if (userScore == 21){
                System.out.println("You won");
                return;
            }
            else if (userScore > 21){
                checkForAce(userCards);
                return;
            }
        }
        else{
            if (dealerScore == 21){
                System.out.println("You won");
                return;
            }
            else if (dealerScore > 21){
                System.out.println("Busted");
                return;
            }
            if (dealerHitCount < 3){
                if (dealerHitCount == 0){
                    dealCard(R.id.dealer2);
                    dealerHitCount++;
                }
                else if (dealerHitCount == 1){
                    dealCard(R.id.dealer3);
                    dealerHitCount++;
                }
                else {
                    dealCard(R.id.dealer4);
                    dealerHitCount++;
                }
            }
            System.out.println(dealerScore);
            if (dealerScore == 21){
                if(userScore == 21){
                    System.out.println("tie");
                }
                else{
                    System.out.println("You lost");
                }

            }
            else if (dealerScore > 21){
                checkForAce(userCards);
            }
        }
    }


    // Resets the view to create a new game
    public void newGame(View myBtn) {
        hitCount = 0;
        userScore = 0;
        dealerScore = 0;
        userCards.clear();
        findViewById(R.id.user0).setBackground(null);
        findViewById(R.id.user1).setBackground(null);
        findViewById(R.id.dealer0).setBackground(null);
        findViewById(R.id.dealer1).setBackground(null);
        initialDeal();
    }

    public void stopGame(View mybtn){
        findViewById(R.id.hitBtn).setClickable(false);
        isDealer = true;
        dealersTurn();
    }

    // Deals two cards to the User and two cards to the Dealer, called at the start of the game
    public void initialDeal() {
        ImageView userFirstCard = findViewById(R.id.user0);
        ImageView userSecondCard = findViewById(R.id.user1);

        ImageView dealerFirstCard = findViewById(R.id.dealer0);
        ImageView dealerSecondCard = findViewById(R.id.dealer1);

        int randInt1 = new Random().nextInt(52);
        int randInt2 = new Random().nextInt(52);

        userCards.add(randInt1);
        userCards.add(randInt2);

        int firstCardScore = cardPulledScore(randInt1, userScore);
        userScore += firstCardScore;
        int secondCardScore = cardPulledScore(randInt2, userScore);
        userScore += secondCardScore;

        System.out.println(userScore);
        if (userScore == 21){
            Log.w("W", "won");
            return;
        }

        int[] cardDeck = fillDeck();
        userFirstCard.setBackgroundResource(cardDeck[randInt1]);
        userSecondCard.setBackgroundResource(cardDeck[randInt2]);

        randInt1 = new Random().nextInt(52);
        randInt2 = new Random().nextInt(52);

        dealerCards.add(randInt1);
        dealerCards.add(randInt2);

        int firstDealerScore = cardPulledScore(randInt1, dealerScore);
        dealerScore += firstDealerScore;
        int secondDealerScore = cardPulledScore(randInt2, dealerScore);
        dealerScore += secondDealerScore;

        dealerFirstCard.setBackgroundResource(cardDeck[randInt1]);
        dealerSecondCard.setBackgroundResource(cardDeck[randInt2]);

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
        while (dealerScore <= userScore || dealerScore < 17){
            findViewById(R.id.hitBtn).performClick();
        }
    }

    public int cardPulledScore(int randInt, int score){
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
            if (score + 11 > 21){
                return 1;
            }
            else {
                return 11;
            }
        }
        else {
            return 10;
        }

    }

    public void checkForAce(ArrayList<Integer> userCards){
        int aceCount = 0;
        for (Integer card: userCards){
            if (card == 36 || card == 37 || card == 38 || card == 39){
                aceCount++;
                if (userScore > 21){
                    if(aceCount > 1){
                        break;
                    }
                    else{
                        userScore -= 11;
                        userScore += 1;
                        System.out.println("Adjusted for ace: " + userScore);
                    }
                }
            }
        }
    }


}