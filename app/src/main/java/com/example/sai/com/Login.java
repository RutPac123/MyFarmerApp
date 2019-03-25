package com.example.sai.com;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sai.myfarmerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private TextView forgot;
    private Button signinbtn;
    private ImageView girlimg;
    private ImageView lockimg;
    private Animation right;
    private Animation left;
    private ProgressDialog progress;
    private CoordinatorLayout mcoordinatorLayout;


    private void updateUI(FirebaseUser currentUser) {
        if (currentUser!=null){
            startActivity(new Intent(Login.this,MainActivity.class));
            finish();
        }else{
            Snackbar.make(mcoordinatorLayout,"Authentication failed! Please Retry",Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.mail);
        password = findViewById(R.id.pass);
        signinbtn = findViewById(R.id.login);
        girlimg = findViewById(R.id.girlkey);
        lockimg = findViewById(R.id.lock);
        forgot = findViewById(R.id.forgotpass);
        right = AnimationUtils.loadAnimation(this,R.anim.slideright);
        left = AnimationUtils.loadAnimation(this,R.anim.slideleft);

        girlimg.startAnimation(right);
        lockimg.startAnimation(left);
        mcoordinatorLayout = findViewById(R.id.mcoordinatorLayout);

        signinbtn.setOnClickListener(v -> {
            if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                Snackbar.make(mcoordinatorLayout,"Empty fields !",Snackbar.LENGTH_SHORT).show();
            }else{
                signIn();
            }

        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             showDialog();
            }
        });

    }

    private void signIn(){
        String emailInput = email.getText().toString();
        String passInput =  password.getText().toString();
        progress = ProgressDialog.show(Login.this, "Please Wait",
                "Signing you in...", true);
        progress.show();
        mAuth.signInWithEmailAndPassword(emailInput, passInput)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progress.hide();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            progress.hide();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset Password");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.forgot_dialog, findViewById(android.R.id.content), false);
// Set up the input
        final EditText inputemail =  viewInflated.findViewById(R.id.input);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(viewInflated);

// Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String m_Email = inputemail.getText().toString();
                if (m_Email.isEmpty()){
                    Snackbar.make(mcoordinatorLayout,"Empty email id!",Snackbar.LENGTH_SHORT).show();
                }else{
                    ForgotPassword forgotPassword = new ForgotPassword();
                    forgotPassword.forgotPass(m_Email,mcoordinatorLayout);
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


}
