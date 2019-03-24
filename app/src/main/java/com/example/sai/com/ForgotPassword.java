package com.example.sai.com;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sai.myfarmerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private Button forgot;
    private EditText mail;
    private String email;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        forgot = findViewById(R.id.login);
        mail = findViewById(R.id.mail);
        firebaseAuth = FirebaseAuth.getInstance();

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mail.getText().toString().trim();
                if (email.equals("")){
                    Toast.makeText(ForgotPassword.this, "Please enter Registered email ID", Toast.LENGTH_SHORT).show();
                }
                else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPassword.this, "Password Reset email sent", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(ForgotPassword.this, "Error in sending password reset email", Toast.LENGTH_SHORT).show();
                            }
                    }
                });
            }
        }
    });
}
}
