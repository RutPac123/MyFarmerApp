package com.example.sai.myfarmer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button loginbtn;
    private EditText email;
    private EditText password;
    private ProgressDialog dialog;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        UpdateUI(user);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginbtn = findViewById(R.id.login);
        email = findViewById(R.id.mail);
        password = findViewById(R.id.pass);


        firebaseAuth = FirebaseAuth.getInstance();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
                    dialog = new ProgressDialog(Login.this);
                    dialog.setTitle("Please Wait....");
                    dialog.setCancelable(false);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.show();
                    new Thread(){
                        public void run(){
                            CreateUser();
                            SignIn();
                        }
                    }.start();

                }else {
                    Toast.makeText(Login.this, "Empty Fields!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    private void UpdateUI(FirebaseUser user){
        if(user!=null){
            Intent intent = new Intent(Login.this,MainActivity.class);
            startActivity(intent);
        }
    }
    public void CreateUser(){
        String getEmail = email.getText().toString();
        String getPass= password.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(getEmail,getPass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser mUser = firebaseAuth.getCurrentUser();
                    UpdateUI(mUser);
                }else {
                    Log.i("MSG","error");
                }
            }
        });

    }
    public void SignIn(){
        String getEmail = email.getText().toString();
        String getPass= password.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(getEmail,getPass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "User is logged in!", Toast.LENGTH_SHORT).show();
                    FirebaseUser mUser = firebaseAuth.getCurrentUser();
                    UpdateUI(mUser);
                }else {
                    Toast.makeText(Login.this, "Login failed! "+ task.getException(), Toast.LENGTH_SHORT).show();

                }
            }

        });

    }
}
