package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[] buttons = new Button[9];
    private Button resetGameButton;
    private int rountCount;
    boolean activePlayer;

    private ImageView playerStatus;
    private ImageView line;

    private Drawable x;
    private Drawable o;
    private Drawable owin;
    private Drawable xwin;
    private Drawable nowin;

    //p1 => 0    //p2 => 1    //empty => 2
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {
            {0, 1, 2, R.drawable.mark6},
            {3, 4, 5, R.drawable.mark8},
            {6, 7, 8, R.drawable.mark7},    //rows
            {0, 3, 6, R.drawable.mark3},
            {1, 4, 7, R.drawable.mark4},
            {2, 5, 8, R.drawable.mark5},    //columns
            {0, 4, 8, R.drawable.mark1},
            {2, 4, 6, R.drawable.mark2}     //cross
    };


    private View.OnClickListener resetClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerStatus = (ImageView) findViewById(R.id.playerStatus);     //initalize playerstatus from xml
        playerStatus.setBackgroundResource(R.drawable.xplay);           //setting background

        line = (ImageView) findViewById(R.id.line);                   //initalize line
        
        resetGameButton = (Button) findViewById(R.id.resetGameButton);

        resetGameButton.setVisibility(View.GONE);
        resetGameButton.setEnabled(false);
        resetGameButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
            }
        });
        // initialize all the buttons
        for (int i = 0; i < buttons.length; i++) {
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }
        rountCount = 0;
        activePlayer = true;
    }


    @Override
    // Code here executes on main thread after user presses button. v is the btn which was clicked
    public void onClick(View v) {
        Log.d("test", "button is clicked!");
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        //Which v (Button) was pressed
        String buttonID = v.getResources().getResourceEntryName(v.getId()); //ex: btn_2
        //we ave the full id, we want only the number
        int gameStatePointerString = Integer.parseInt(buttonID.substring(buttonID.length() - 1, buttonID.length()));      //2

        if(gameState[gameStatePointerString] != 2 || checkWinner()){
            return;
        }
        // x begin
        if (activePlayer) {
            ((Button) v).setBackgroundResource(R.drawable.x);
            playerStatus.setBackgroundResource(R.drawable.oplay);
            gameState[gameStatePointerString] = 0;
        } else {
            ((Button) v).setBackgroundResource(R.drawable.o);
            playerStatus.setBackgroundResource(R.drawable.xplay);
            gameState[gameStatePointerString] = 1;
        }
        rountCount++;

        //Check for our winner (which player is playing)
        if(checkWinner()){
            if(activePlayer){       //meaning player1 is playing
                playerStatus.setBackgroundResource(R.drawable.xwin);
            }
            else{
                playerStatus.setBackgroundResource(R.drawable.owin);
            }

        }   //no winner
        else if(rountCount == 9){
            playerStatus.setBackgroundResource(R.drawable.nowin);

            resetGameButton.setVisibility(View.VISIBLE);
            resetGameButton.setEnabled(true);
        }
        else {
            activePlayer = !activePlayer;
        }
    }


    //Checking for winner
    public boolean checkWinner() {
        boolean winnerResult = false;
        //each turn we need to check for every row in winningPosition if each column are equals
        //(Ex: GameState[0]=GameState[1]=GameState[2]=0 / 1)
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {
                winnerResult = true;

                line.setBackgroundResource(winningPosition[3]);

                resetGameButton.setVisibility(View.VISIBLE);
                resetGameButton.setEnabled(true);
            }
        }

        return winnerResult;
    }





        public void playAgain(){
        playerStatus.setBackgroundResource(R.drawable.xplay);
        line.setBackgroundResource(0);
            activePlayer = true;
            rountCount = 0;
            resetGameButton.setEnabled(false);
            resetGameButton.setVisibility(View.GONE);

            for (int i = 0; i < buttons.length; i++) {
                gameState[i] = 2;   //for empty buttons
                buttons[i].setBackgroundResource(0);     //to empty background
          }
        }
    }







