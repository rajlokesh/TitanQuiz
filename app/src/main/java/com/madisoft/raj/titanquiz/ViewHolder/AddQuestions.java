package com.madisoft.raj.titanquiz.ViewHolder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.madisoft.raj.titanquiz.Common.Common;
import com.madisoft.raj.titanquiz.Done;
import com.madisoft.raj.titanquiz.Home;
import com.madisoft.raj.titanquiz.Models.Question;
import com.madisoft.raj.titanquiz.R;

public class AddQuestions extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference questions;
    EditText edtQuestion,edtChoiceA,edtChoiceB, edtChoiceC, edtChoiceD;
    Spinner spinner;
    TextView totalIn;
    String quizId;
    Button btnAppend,btnFinish;
    int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);

        database=FirebaseDatabase.getInstance();
        questions=database.getReference("Questions");

        edtQuestion=findViewById(R.id.edtQuestion);
        edtChoiceA=findViewById(R.id.edtChoiceA);
        edtChoiceB=findViewById(R.id.edtChoiceB);
        edtChoiceC=findViewById(R.id.edtChoiceC);
        edtChoiceD=findViewById(R.id.edtChoiceD);
        spinner=findViewById(R.id.spinner);
        totalIn=findViewById(R.id.totalIn);
        btnAppend=findViewById(R.id.btnAppend);
        btnFinish=findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddQuestions.this,Home.class);
                startActivity(intent);
                finish();
            }
        });
        Bundle extra = getIntent().getExtras();
        if(extra!=null){
            quizId=extra.getString("QUIZID");

        }
        btnAppend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtQuestion.getText().length()<1||edtChoiceA.getText().length()<1||edtChoiceB.getText().length()<1||edtChoiceC.getText().length()<1||edtChoiceD.getText().length()<1){
                    Toast.makeText(AddQuestions.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    questions.child(quizId+"_"+(++index)).setValue(new Question(
                            edtQuestion.getText().toString(),
                            edtChoiceA.getText().toString(),
                            edtChoiceB.getText().toString(),
                            edtChoiceC.getText().toString(),
                            edtChoiceD.getText().toString(),
                            getAnswer(spinner.getSelectedItemPosition()),
                            quizId


                            ));
                    totalIn.setText(index+" Questions added");

                    edtQuestion.getText().clear();
                            edtChoiceA.getText().clear();
                            edtChoiceB.getText().clear();
                            edtChoiceC.getText().clear();
                            edtChoiceD.getText().clear();
                            spinner.setSelection(0);
                }
            }

            private String getAnswer(int selectedItemPosition) {
                switch (selectedItemPosition){
                    case 0:
                        return edtChoiceA.getText().toString();
                    case 1:
                        return edtChoiceB.getText().toString();

                    case 2:
                        return edtChoiceC.getText().toString();

                    default:
                        return edtChoiceD.getText().toString();


                }
            }
        });




    }
}
