package com.madisoft.raj.titanquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.madisoft.raj.titanquiz.Common.Common;
import com.madisoft.raj.titanquiz.Models.Category;
import com.madisoft.raj.titanquiz.ViewHolder.AddQuestions;


public class CreateQuizFragment extends Fragment {

    View myFragment;
    FirebaseDatabase database;
    DatabaseReference categories;
    EditText edtQuizId,edtQuizName,edtQuizDescription;
    Button addQues;
    public static CreateQuizFragment newInstance(){
        CreateQuizFragment createQuizFragmentFragment = new CreateQuizFragment();
        return createQuizFragmentFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        categories = database.getReference("Category");

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myFragment = inflater.inflate(R.layout.fragment_createquiz,container,false);
        edtQuizId= myFragment.findViewById(R.id.edtQuizId);
        edtQuizName= myFragment.findViewById(R.id.edtQuizName);
        edtQuizDescription= myFragment.findViewById(R.id.edtQuizDescription);
        addQues=myFragment.findViewById(R.id.btnAddQuestions);

        addQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtQuizId.getText().length()<1||edtQuizName.getText().length()<1||edtQuizDescription.getText().length()<1)
                    Toast.makeText(myFragment.getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                else {
                    categories.child(edtQuizId.getText().toString())
                            .setValue(new Category(edtQuizName.getText().toString(), edtQuizDescription.getText().toString()));
                    Intent addQues = new Intent(getActivity(),AddQuestions.class);
                    Bundle dataSend = new Bundle();

                    dataSend.putString("QUIZID",edtQuizId.getText().toString());
                    addQues.putExtras(dataSend);

                    startActivity(addQues);
                }


            }
        });

        return myFragment;
    }


}
