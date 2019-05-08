package com.madisoft.raj.titanquiz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.madisoft.raj.titanquiz.Common.Common;
import com.madisoft.raj.titanquiz.Models.Question;

import java.util.Collections;

public class Start extends AppCompatActivity {

    Button btnPlay;
    FirebaseDatabase database;
    DatabaseReference questions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        database=FirebaseDatabase.getInstance();
        questions=database.getReference("Questions");

        loadQuestion(Common.categoryId);
        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(Start.this,Quiz.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadQuestion(String categoryId) {

        if(Common.questionList.size()>0)
            Common.questionList.clear();

        questions.orderByChild("categoryId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                            Question ques = postSnapshot.getValue(Question.class);
                            Common.questionList.add(ques);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        Collections.shuffle(Common.questionList);
    }
}
