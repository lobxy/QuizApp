package com.lobxy.quizapp.Admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lobxy.quizapp.Model.Question;
import com.lobxy.quizapp.R;
import com.lobxy.quizapp.Utils.Connectivity;

import java.util.ArrayList;
import java.util.List;

public class AddQuestion extends AppCompatActivity {

    //for correct answer
    private Spinner spinner;

    private EditText et_question;

    private EditText et_option1;
    private EditText et_option2;
    private EditText et_option3;
    private EditText et_option4;

    private String question, option1, option2, option3, option4, correctAnswer;

    private DatabaseReference reference;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        reference = FirebaseDatabase.getInstance().getReference("Questions");

        Button submit = findViewById(R.id.aqSubmit);

        progressBar = findViewById(R.id.aqProgressBar);

        spinner = findViewById(R.id.aqSpinnerCorrectAnswer);

        et_option1 = findViewById(R.id.aqOption1);
        et_option2 = findViewById(R.id.aqOption2);
        et_option3 = findViewById(R.id.aqOption3);
        et_option4 = findViewById(R.id.aqOption4);
        et_question = findViewById(R.id.aqQuestion);

        //feed data into spinner
        List<String> list = new ArrayList<>();
        list.add("Select");
        list.add("Option1");
        list.add("Option2");
        list.add("Option3");
        list.add("Option4");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                correctAnswer = adapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void validate() {
        Connectivity connectivity = new Connectivity(this);

        question = et_question.getText().toString().trim();

        option1 = et_option1.getText().toString().trim();
        option2 = et_option2.getText().toString().trim();
        option3 = et_option3.getText().toString().trim();
        option4 = et_option4.getText().toString().trim();

        correctAnswer = spinner.getSelectedItem().toString();

        if (correctAnswer.equals("Select")) {
            showToast("Select correct answer");
            return;
        }
        if (option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || option4.isEmpty()) {
            showToast("Field empty");
            return;
        }


        if (connectivity.check()) submitQuestion();
        else showToast("Internet connection not found");

    }

    private void submitQuestion() {
        progressBar.setVisibility(View.VISIBLE);

        //set difficulty level - for future

        String id = reference.push().getKey();

        //feed data and upload data into db
        Question ques = new Question(id, question, option1, option2, option3, option4, correctAnswer);

        reference.child(id).setValue(ques).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.INVISIBLE);

                if (task.isSuccessful()) {
                    showToast("Question Added");
                    et_option1.setText("");
                    et_option2.setText("");
                    et_option3.setText("");
                    et_option4.setText("");
                    et_question.setText("");
                } else {
                    showToast(task.getException().getMessage());
                }

            }
        });

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
