package com.example.sai.com;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private FirebaseAuth firebaseAuthinstance = FirebaseAuth.getInstance();

    public void forgotPass(String emailid ,CoordinatorLayout coordinatorLayout){
        if (emailid.equals("")){
            Snackbar.make(coordinatorLayout,"Enter registered email id !",Snackbar.LENGTH_SHORT).show();
        }
        else {
            firebaseAuthinstance.sendPasswordResetEmail(emailid).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Snackbar.make(coordinatorLayout,"Reset link is sent to your email id",Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        Snackbar.make(coordinatorLayout,"Error resetting the password!",Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}