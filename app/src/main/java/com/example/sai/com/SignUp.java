package com.example.sai.com;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.example.sai.myfarmerapp.R;


public class SignUp extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText email, pass;
    Button singup;
    String myEmail, myPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.mail);
        pass = findViewById(R.id.pass);
        singup = findViewById(R.id.signup);

        mAuth = FirebaseAuth.getInstance();

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myEmail = email.getText().toString();
                myPass = pass.getText().toString();
                CreateNewUser();
            }
        });
        }

        private void CreateNewUser(){
        mAuth.createUserWithEmailAndPassword(myEmail, myPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SignUp.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        }
}
