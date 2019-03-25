package com.example.sai.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sai.myfarmerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register2 extends AppCompatActivity {

    TextView name;
    LinearLayout input1,input2,input3;
    Animation slideleft,slideright;
    private EditText mailid,pass,confirmpass;
    FloatingActionButton mFloatingActionButton;
    private FirebaseAuth firebaseAuth;
    private String emailString;
    private String nametxt;
    private String usernameText;
    private CoordinatorLayout mcoordinatorLayout;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_register);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        usernameText = getIntent().getStringExtra("name");
        name= findViewById(R.id.name);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        input3 = findViewById(R.id.input3);
        mailid = findViewById(R.id.mail);
        pass = findViewById(R.id.pass1);
        confirmpass = findViewById(R.id.pass2);
        mcoordinatorLayout = findViewById(R.id.coordinatorLayout);


        firebaseAuth = FirebaseAuth.getInstance();
        mFloatingActionButton = findViewById(R.id.faBtnlg2);


        SharedPreferences preferences = getSharedPreferences("NAME",MODE_PRIVATE);
        nametxt = preferences.getString("uName",null);

        if (nametxt.length()>6) {
            nametxt = nametxt.substring(0,6)+"...";
        }

        slideleft = AnimationUtils.loadAnimation(this,R.anim.slideleft);
        slideright = AnimationUtils.loadAnimation(this,R.anim.slideright);

        input1.setAnimation(slideleft);
        input2.setAnimation(slideleft);
        input3.setAnimation(slideleft);

        name.setText("Hi, "+ nametxt+" !");
        name.setAnimation(slideright);

        mFloatingActionButton.setOnClickListener(v -> {
            if(!pass.getText().toString().equals(confirmpass.getText().toString())){
                Snackbar snackbar = Snackbar.make(v,"Passwords do not match!",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }else if(pass.getText().toString().isEmpty() || confirmpass.getText().toString().isEmpty()
                    ||  pass.getText().toString().isEmpty()){
                Snackbar snackbar = Snackbar.make(v,"Empty fields!",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
            else{
                createAccount();
            }

        });
    }
    private void createAccount(){
        emailString = mailid.getText().toString();

        final String passString = pass.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(emailString, passString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            databaseReference = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Mail id");
                            databaseReference.setValue(emailString);
                            databaseReference.child("User name").setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                            updateUI(firebaseAuth.getCurrentUser());

                        }
                        if (!task.isSuccessful())
                        {
                            try
                            {
                                throw task.getException();
                            }


                            catch (FirebaseAuthWeakPasswordException weakPass){
                                Snackbar.make(mcoordinatorLayout,"Make a minimum 6 characters password ! May include symbols ,numbers,letters combination.",Snackbar.LENGTH_SHORT).show();

                            }
                            catch (FirebaseAuthUserCollisionException existEmail)
                            {
                                Snackbar.make(mcoordinatorLayout,"This account already exist!",Snackbar.LENGTH_SHORT).show();
                            }
                            catch (Exception e)
                            {
                                Snackbar.make(mcoordinatorLayout,"Please try again!",Snackbar.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user!=null){
            Toast.makeText(this, "User signed in!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Register2.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
