package com.lobxy.quizapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuizScreen extends AppCompatActivity implements View.OnClickListener {

    //TODO: add other functions like: +-,/,*,% etc.

    private static final String TAG = "Quiz";
    public static int QUIZ_LENGTH = 30; //in secs

    Button resetButton;
    TextView questionTextView, scoreBoard, timerTextView;

    int max = 99;
    int min = 0;

    Button[] buttons = new Button[4];

    int solution = 0;
    int score = 0;
    int noOfPlays = 0;

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_screen);

        questionTextView = findViewById(R.id.questionTextView);
        scoreBoard = findViewById(R.id.scoreBoard);
        timerTextView = findViewById(R.id.timer);

        resetButton = findViewById(R.id.reset);

        //initialize buttons.

        for (int j = 0; j < 4; j++) {
            String buttonId = "button_" + j;
            int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
            buttons[j] = findViewById(resId);
            buttons[j].setOnClickListener(this);
        }

        createRandom();

        countDownTimer = new CountDownTimer(QUIZ_LENGTH * 1000, 1000) {
            @Override
            public void onTick(long l) {
                timerTextView.setText("Timer: " + String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuizScreen.this);
                builder.setTitle("TIMES UP")
                        .setMessage("Score: " + score)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                reset();
                            }
                        })
                        .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }.start();

        resetButton.setOnClickListener(this);
    }


    private void createRandom() {
        //set score
        scoreBoard.setText(String.valueOf(score) + "/" + String.valueOf(noOfPlays));

        noOfPlays++;
        int varOne = new Random().nextInt(max - min + 1) + min;
        int varTwo = new Random().nextInt(max - min + 1) + min;

        questionTextView.setText(String.valueOf(varOne) + " + " + String.valueOf(varTwo));
        setValues(varOne, varTwo);
    }

    private void setValues(int varOne, int varTwo) {
        int ran = new Random().nextInt(3);

        //generate options
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            numbers.add((varOne + varTwo) + (i * ran * 2) - 1);
        }
        Collections.shuffle(numbers);

        //get a random point for solution.

        //set them to buttons
        for (int i = 0; i < 4; i++) {
            if (i == ran) {
                //if the point i is equal to the solution point, set solution to that button.
                solution = varOne + varTwo;

                buttons[i].setText(String.valueOf(solution));
            } else {
                String value = numbers.get(i).toString();
                buttons[i].setText(value);
            }
        }
    }


    private void reset() {
        //reset the timer and restart the game.
        countDownTimer.start();

        score = 0;
        noOfPlays = 0;
        solution = 0;

        createRandom();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reset:
                reset();
                break;
            case R.id.button_0:
                checkForSolution(0);
                break;
            case R.id.button_1:
                checkForSolution(1);
                break;
            case R.id.button_2:
                checkForSolution(2);
                break;
            case R.id.button_3:
                checkForSolution(3);
                break;
        }
    }

    private void checkForSolution(int position) {
        Log.i(TAG, "value :" + buttons[position].getText().toString());
        Log.i(TAG, "solution :" + solution);
        if (String.valueOf(solution).equals(buttons[position].getText().toString())) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            score++;
        } else {
            Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
        }
        createRandom();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
        finish();
    }
}
