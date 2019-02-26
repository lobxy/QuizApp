package com.lobxy.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.lobxy.quizapp.Online.OnlineQuizActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goToOfflineQuizScreen = findViewById(R.id.offlineButton);

        Button goToOnlineQuizScreen = findViewById(R.id.onlineButton);

    }


    public void startOffline(View view) {
        startActivity(new Intent(this, QuizScreen.class));
        finish();
    }

    public void startOnline(View view) {
        startActivity(new Intent(this, OnlineQuizActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.admin_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                startActivity(new Intent(this, LoginSignUpActivity.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
