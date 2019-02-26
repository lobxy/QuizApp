package com.lobxy.quizapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lobxy.quizapp.R;

class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView question;

    TextView option1;
    TextView option2;
    TextView option3;
    TextView option4;

    TextView correctAnswer;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        question = itemView.findViewById(R.id.itemQuestion);

        option1 = itemView.findViewById(R.id.itemOption1);
        option2 = itemView.findViewById(R.id.itemOption2);
        option3 = itemView.findViewById(R.id.itemOption3);
        option4 = itemView.findViewById(R.id.itemOption4);

        correctAnswer = itemView.findViewById(R.id.itemCorrectAnswer);

    }
}
