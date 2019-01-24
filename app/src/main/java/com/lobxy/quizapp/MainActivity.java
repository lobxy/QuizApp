package com.lobxy.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goToQuizScreen = findViewById(R.id.button);
    }


    public void start(View view) {
        startActivity(new Intent(this, QuizScreen.class));
        finish();
    }
}
