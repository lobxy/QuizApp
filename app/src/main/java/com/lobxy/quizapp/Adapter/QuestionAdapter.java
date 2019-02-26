package com.lobxy.quizapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lobxy.quizapp.Model.Question;
import com.lobxy.quizapp.R;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    ArrayList<Question> questions;

    public QuestionAdapter(ArrayList<Question> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.admin_recycler_item, null);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int i) {
        Question question = questions.get(i);

        holder.question.setText(question.getQuestion());

        holder.option1.setText(question.getOption1());
        holder.option2.setText(question.getOption2());
        holder.option3.setText(question.getOption3());
        holder.option4.setText(question.getOption4());

        holder.correctAnswer.setText(question.getCorrectAnswer());

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
