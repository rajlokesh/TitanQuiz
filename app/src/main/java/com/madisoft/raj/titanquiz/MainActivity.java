package com.madisoft.raj.titanquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.madisoft.raj.titanquiz.Common.Common;
import com.madisoft.raj.titanquiz.Models.User;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {
    MaterialEditText edtNewUser, edtNewPassword, edtNewEmail;
    MaterialEditText edtUser,edtPassword;

    Button btnSignuUp, btnSignIn;

    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database=FirebaseDatabase.getInstance();
        users= database.getReference("Users");

        edtUser = (MaterialEditText) findViewById(R.id.edtUserName);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);

        btnSignIn=(Button) findViewById(R.id.btn_sign_in);
        btnSignuUp=(Button) findViewById(R.id.btn_sign_up);

        btnSignuUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpDialog();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(edtUser.getText().toString(),edtPassword.getText().toString());
                
            }
        });
    }

    private void signIn(final String user, final String pwd) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(user).exists()){
                    if(!user.isEmpty()){
                        User login = dataSnapshot.child(user).getValue(User.class);
                        Log.d(login.getUserName(),login.getPassword());
                        if(login.getPassword().equals(pwd)) {
                            Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                            Intent homeActivity = new Intent(MainActivity.this,Home.class);
                            Common.currentUser=login;
                            startActivity(homeActivity);
                            finish();
                        }
                        else
                            Toast.makeText(MainActivity.this,"Wrong password",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"User name please",Toast.LENGTH_SHORT).show();

                    }
                }
                else
                    Toast.makeText(MainActivity.this,"User name does not exist!!!",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showSignUpDialog() {
        AlertDialog.Builder alertDialog  = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Sign Up");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater inflater = this.getLayoutInflater();
        View sign_up_layout = inflater.inflate(R.layout.sign_up_layout,null);

        edtNewUser= (MaterialEditText)sign_up_layout.findViewById(R.id.edtNewUserName);
        edtNewEmail= (MaterialEditText)sign_up_layout.findViewById(R.id.edtNewEmail);
        edtNewPassword= (MaterialEditText)sign_up_layout.findViewById(R.id.edtNewPassword);

        alertDialog.setView(sign_up_layout);
        alertDialog.setIcon(R.drawable.ic_people_outline_black_24dp);

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final User user = new User(edtNewUser.getText().toString(),
                        edtNewPassword.getText().toString(),edtNewEmail.getText().toString());
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(user.getUserName()).exists())
                            Toast.makeText(MainActivity.this,"User already exists!!!",Toast.LENGTH_SHORT).show();
                        else{
                            users.child(user.getUserName())
                                    .setValue(user);
                            Toast.makeText(MainActivity.this,"User registration success",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                dialog.dismiss();
            }

        });

        alertDialog.show();
    }
}
