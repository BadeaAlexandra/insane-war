package com.example.insanewar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {

    public void goBack(View view) {
        finish();
    }

    //region declarari

    private ImageView backCard1, backCard2;
    private ImageView userCard, opponentCard;
    private TextView userNameTextView, opponentNameTextView;
    private TextView userScoreTextView, opponentScoreTextView;
    private Button startGameButton;

    private int[] viz = new int[53];
    private int noCardsUser = 26, noCardsOpponent = 26;
    private String[] userCards = new String[54];
    private String[] opponentCards = new String[54];

    private int roundNo = 1;
    int count;
    private int noOfWarCards;
    private int userCardID;
    private int opponentCardID;

    //endregion

    public String getCardName(int i) {
        String cardName = null;
        switch (i) {
            case 1:
                cardName = "c01";
                break;
            case 2:
                cardName = "c02";
                break;
            case 3:
                cardName = "c03";
                break;
            case 4:
                cardName = "c04";
                break;
            case 5:
                cardName = "c05";
                break;
            case 6:
                cardName = "c06";
                break;
            case 7:
                cardName = "c07";
                break;
            case 8:
                cardName = "c08";
                break;
            case 9:
                cardName = "c09";
                break;
            case 10:
                cardName = "c10";
                break;
            case 11:
                cardName = "c11";
                break;
            case 12:
                cardName = "c12";
                break;
            case 13:
                cardName = "c13";
                break;

            case 14:
                cardName = "d01";
                break;
            case 15:
                cardName = "d02";
                break;
            case 16:
                cardName = "d03";
                break;
            case 17:
                cardName = "d04";
                break;
            case 18:
                cardName = "d05";
                break;
            case 19:
                cardName = "d06";
                break;
            case 20:
                cardName = "d07";
                break;
            case 21:
                cardName = "d08";
                break;
            case 22:
                cardName = "d09";
                break;
            case 23:
                cardName = "d10";
                break;
            case 24:
                cardName = "d11";
                break;
            case 25:
                cardName = "d12";
                break;
            case 26:
                cardName = "d13";
                break;

            case 27:
                cardName = "h01";
                break;
            case 28:
                cardName = "h02";
                break;
            case 29:
                cardName = "h03";
                break;
            case 30:
                cardName = "h04";
                break;
            case 31:
                cardName = "h05";
                break;
            case 32:
                cardName = "h06";
                break;
            case 33:
                cardName = "h07";
                break;
            case 34:
                cardName = "h08";
                break;
            case 35:
                cardName = "h09";
                break;
            case 36:
                cardName = "h10";
                break;
            case 37:
                cardName = "h11";
                break;
            case 38:
                cardName = "h12";
                break;
            case 39:
                cardName = "h13";
                break;

            case 40:
                cardName = "s01";
                break;
            case 41:
                cardName = "s02";
                break;
            case 42:
                cardName = "s03";
                break;
            case 43:
                cardName = "s04";
                break;
            case 44:
                cardName = "s05";
                break;
            case 45:
                cardName = "s06";
                break;
            case 46:
                cardName = "s07";
                break;
            case 47:
                cardName = "s08";
                break;
            case 48:
                cardName = "s09";
                break;
            case 49:
                cardName = "s10";
                break;
            case 50:
                cardName = "s11";
                break;
            case 51:
                cardName = "s12";
                break;
            case 52:
                cardName = "s13";
                break;
            default:
                break;
        }

        return cardName;
    }

    public int cardValue(String uc, String oc) {   //ret 1 dc uc > oc
        int u = Integer.parseInt(uc.substring(1));
        int o = Integer.parseInt(oc.substring(1));
        if (u == o) return 0;
        else if (u == 1) return 1;
        else if (o == 1) return -1;
        else if (u > o) return 1;
        else return -1;  // (o == 1 || o > u)
    }

    public void splitPack() {
        for (int i = 1; i <= 52; i++) viz[i] = 0;
        int k = 0, i;
        while (k < noCardsUser) {
            Random r = new Random();
            i = r.nextInt(52) + 1;
            if (viz[i] == 0) {
                viz[i] = 1;
                k++;
                userCards[k] = getCardName(i);
            }
        }
        k = 0;
        while (k < noCardsOpponent) {
            Random r = new Random();
            i = r.nextInt(52) + 1;
            if (viz[i] == 0) {
                viz[i] = 1;
                k++;
                opponentCards[k] = getCardName(i);
            }
        }

    }

    public void userWinner() {
        userCards[noCardsUser + 1] = userCards[1];
        userCards[noCardsUser + 2] = opponentCards[1];

        for (int i = 1; i <= noCardsUser + 1; i++)
            userCards[i] = userCards[i + 1];

        for (int i = 1; i <= noCardsOpponent - 1; i++)
            opponentCards[i] = opponentCards[i + 1];

        noCardsUser += 1;
        noCardsOpponent -= 1;
    }

    public void opponentWinner() {
        opponentCards[noCardsOpponent + 1] = opponentCards[1];
        opponentCards[noCardsOpponent + 2] = userCards[1];

        for (int i = 1; i <= noCardsOpponent + 1; i++)
            opponentCards[i] = opponentCards[i + 1];

        for (int i = 1; i <= noCardsUser - 1; i++)
            userCards[i] = userCards[i + 1];

        noCardsUser -= 1;
        noCardsOpponent += 1;
    }

    public void newRound() {

        startGameButton.setText("Runda " + roundNo);
        roundNo++;

        userScoreTextView.setText(noCardsUser + " carti");
        opponentScoreTextView.setText(noCardsOpponent + " carti");

//        userScoreTextView.setText(noCardsUser + " carti" + userCards[1] + " " + userCards[2]);
//        opponentScoreTextView.setText(noCardsOpponent + " carti" + opponentCards[1] + " " + opponentCards[2] );

//        userScoreTextView.setText(noCardsUser + " carti " + userCards[noCardsUser - 1] + " " + userCards[noCardsUser]);
//        opponentScoreTextView.setText(noCardsOpponent + " carti " + opponentCards[noCardsOpponent - 1] + " " + opponentCards[noCardsOpponent]);
//        userScoreTextView.setText(noCardsUser + " carti " + userCards[1]);
//        opponentScoreTextView.setText(noCardsOpponent + " carti " + opponentCards[1]);

        userCardID = getResources().getIdentifier(userCards[1], "drawable", getPackageName());
        userCard.setImageResource(userCardID);

        opponentCardID = getResources().getIdentifier(opponentCards[1], "drawable", getPackageName());
        opponentCard.setImageResource(opponentCardID);

        userCard.setVisibility(View.VISIBLE);
        opponentCard.setVisibility(View.VISIBLE);

        if (cardValue(userCards[1], opponentCards[1]) == 1) {
            userWinner();
        }
        else if (cardValue(userCards[1], opponentCards[1]) == -1) {
            opponentWinner();
        }

        else while (cardValue(userCards[1], opponentCards[1]) == 0)  {

            noOfWarCards = Integer.parseInt(userCards[1].substring(1));

            if (noOfWarCards == 1) noOfWarCards = 14;

            //region derulare carti razboi

                count = 1;

                Thread t = new Thread() {
                    @Override
                    public void run() {
                        while (count < noOfWarCards) {

                            try {
                                Thread.sleep(2000);  //1000ms = 1 sec

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                      //  startGameButton.setEnabled(false);

                                        count++;
                                        userCardID = getResources().getIdentifier(userCards[count], "drawable", getPackageName());
                                        userCard.setImageResource(userCardID);
                                        opponentCardID = getResources().getIdentifier(opponentCards[count], "drawable", getPackageName());
                                        opponentCard.setImageResource(opponentCardID);

                                     //   if (noOfWarCards - count == 0)
                                     //       startGameButton.setEnabled(true);
                                    }
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };

                t.start();

            //endregion



            if (cardValue(userCards[noOfWarCards + 1], opponentCards[noOfWarCards + 1]) == 1) {

                for (int i = 1; i <= noOfWarCards + 1; i++)
                    userCards[noCardsUser + i] = userCards[i];

                for (int i = 1; i <= noOfWarCards + 1; i++)
                    userCards[noCardsUser + noOfWarCards + i + 1] = opponentCards[i];


                for (int i = 1; i < noCardsUser + 2 * (noOfWarCards + 1); i++)
                    userCards[i] = userCards[i + 1];

                for (int i = 1; i <= noCardsOpponent - 1; i++)
                    opponentCards[i] = opponentCards[i + 1];

                noCardsUser += noOfWarCards + 1;
                noCardsOpponent -= (noOfWarCards + 1);
            }

            else if (cardValue(userCards[noOfWarCards + 1],opponentCards[noOfWarCards + 1]) == -1) {

                for (int i = 1; i <= noOfWarCards + 1; i++)
                    opponentCards[noCardsOpponent + i] = opponentCards[i];

                for (int i = 1; i <= noOfWarCards + 1; i++)
                    opponentCards[noCardsOpponent + noOfWarCards + i + 1] = userCards[i];


                for (int i = 1; i < noCardsOpponent + 2 * (noOfWarCards + 1); i++)
                    opponentCards[i] = opponentCards[i + 1];

                for (int i = 1; i <= noCardsUser - 1; i++)
                    userCards[i] = userCards[i + 1];

                noCardsOpponent += noOfWarCards + 1;
                noCardsUser -= (noOfWarCards + 1);
            }
        }

    }

    public void onStartClick (View view) {

        if (noCardsUser == 1) backCard2.setVisibility(View.INVISIBLE);
            else backCard2.setVisibility(View.VISIBLE);

        if (noCardsOpponent == 1) backCard1.setVisibility(View.INVISIBLE);
            else backCard1.setVisibility(View.VISIBLE);

        if (noCardsUser != 0 && noCardsOpponent != 0)
            newRound();
        else if (noCardsUser == 0)
             Toast.makeText(GameActivity.this,"Castigatorul este: " + opponentNameTextView.getText().toString(), Toast.LENGTH_LONG).show();
        else Toast.makeText(GameActivity.this,"Castigatorul este: " + userNameTextView.getText().toString(), Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        //region initializari

        backCard1 = findViewById(R.id.backCard1);
        backCard2 = findViewById(R.id.backCard2);
        userCard = findViewById(R.id.userCard);
        opponentCard = findViewById(R.id.opponentCard);
        userNameTextView = findViewById(R.id.userNameTextView);
        opponentNameTextView = findViewById(R.id.opponentNameTextView);
        userScoreTextView = findViewById(R.id.userScoreTextView);
        opponentScoreTextView = findViewById(R.id.opponentScoreTextView);
        startGameButton = findViewById(R.id.startGameButton);

        userNameTextView.setText(username);
        opponentNameTextView.setText("Andreea90");

        userScoreTextView.setText("26 carti");
        opponentScoreTextView.setText("26 carti");

        userCard.setVisibility(View.INVISIBLE);
        opponentCard.setVisibility(View.INVISIBLE);

        backCard1.setVisibility(View.VISIBLE);
        backCard2.setVisibility(View.VISIBLE);

        //endregion

        splitPack();

    }
}
