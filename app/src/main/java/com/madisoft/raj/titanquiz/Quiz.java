package com.madisoft.raj.titanquiz;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.madisoft.raj.titanquiz.Common.Common;

public class Quiz extends AppCompatActivity implements View.OnClickListener {

     static long INTERVAL= 1000;
     static long TIMEOUT= 10000;

    CountDownTimer mCountDown;

    int index=0,score=0,thisQuestion=0,totalQuestion,correctAnswer,progressValue;

    StringBuilder rev= new StringBuilder(" ");



    ProgressBar progressBar;

    Button btnA,btnB,btnC,btnD;
    TextView txtScore,txtQuestionNum,question_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);



        txtScore = (TextView) findViewById(R.id.txtScore);
        txtQuestionNum= (TextView) findViewById(R.id.txtTotalQuestion);
        question_text=(TextView)findViewById(R.id.question_text);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnA= (Button)findViewById(R.id.btnAnswerA);
        btnB= (Button)findViewById(R.id.btnAnswerB);
        btnC= (Button)findViewById(R.id.btnAnswerC);
        btnD= (Button)findViewById(R.id.btnAnswerD);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        if(index<totalQuestion)
        {
            Button clickedButton = (Button)v;
            if(clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer()))
            {
                score+=1;
                correctAnswer++;

                rev.append("  \u2713"+"\nCorrect Answer: "+clickedButton.getText()+"\n");

            }
            else {
                rev.append("  \u274C"+"\nSelected : "+clickedButton.getText()+"\nCorrect Answer: "+Common.questionList.get(index).getCorrectAnswer()+"\n");
            }
            /*else{
                Intent intent= new Intent(this,Done.class);
                Bundle dataSend = new Bundle();
                dataSend.putInt("SCORE",score);
                dataSend.putInt("TOTAL",totalQuestion);
                dataSend.putInt("CORRECT",correctAnswer);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();
            }*/
            showQuestion(++index);
            txtScore.setText(String.format("%d",score));

        }

    }

    private void showQuestion(int index) {
        if(index<totalQuestion){

            thisQuestion++;
            txtQuestionNum.setText(String.format("%d / %d",thisQuestion,totalQuestion));

            question_text.setText(Common.questionList.get(index).getQuestion());
            btnA.setText(Common.questionList.get(index).getAnswerA());
            btnB.setText(Common.questionList.get(index).getAnswerB());
            btnC.setText(Common.questionList.get(index).getAnswerC());
            btnD.setText(Common.questionList.get(index).getAnswerD());
            rev.append("\n"+(index+1)+") "+Common.questionList.get(index).getQuestion());


        }
        else{
            mCountDown.cancel();
            Intent intent= new Intent(this,Done.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE",score);
            dataSend.putInt("TOTAL",totalQuestion);
            dataSend.putInt("CORRECT",correctAnswer);
            dataSend.putString("REVIEW",rev.toString());
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }
    }



    @Override
    protected void onStop() {
        super.onStop();
        mCountDown.cancel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCountDown.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();

        totalQuestion= Common.questionList.size();
        TIMEOUT = TIMEOUT*totalQuestion;

        progressBar.setMax((int)(TIMEOUT));
        progressBar.setProgress(0);

        mCountDown = new CountDownTimer(TIMEOUT,INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressValue+=INTERVAL;
                ObjectAnimator.ofInt(progressBar, "progress", progressValue)
                        .setDuration(1000)
                        .start();
                //progressBar.setProgress(progressValue);

            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                showQuestion(totalQuestion);

            }
        };
        mCountDown.start();
        showQuestion(index);
    }
}
