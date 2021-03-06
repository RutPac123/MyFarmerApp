package com.example.sai.com;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sai.myfarmerapp.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;


public class OTP extends AppCompatActivity {

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private View parentlayout;
    ProgressDialog progress;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private TextView t1;
    private ImageView i1,i2;
    private Animation bounceanime,moveanime;
    private EditText e1;
    private Button b1;
   // private boolean isFirstRun=true;
    private TextView exampleTxt;
    private SharedPreferences preferences;
    private CountryCodePicker codePicker;
    private String number;
    private View view;
    private CoordinatorLayout coordinatorLayout;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    private String text;
    private TextView mcurrentPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_otp);
        mcurrentPhone = findViewById(R.id.current_phone);

        loadPhone();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        // getting first run boolean value(false) after 1st run
        SharedPreferences getpref = getSharedPreferences("FIRST_RUN",MODE_PRIVATE);
       // isFirstRun = getpref.getBoolean("checkrunstatus",true);

//        if(!isFirstRun){  // if its not the 1st run
//            Intent intent = new Intent(OTP.this,LoginBoth.class);
//            startActivity(intent);
//            finish();
//        }
        parentlayout = findViewById(android.R.id.content);
        exampleTxt = findViewById(R.id.exampletxt);
        e1 =  findViewById(R.id.Phonenoedittext);
        b1 = findViewById(R.id.PhoneVerify);
        t1 = findViewById(R.id.textView2Phone);
        i2 = findViewById(R.id.logo);
        codePicker = findViewById(R.id.ccp);
        view = findViewById(R.id.mView);
        codePicker.registerCarrierNumberEditText(e1);
        coordinatorLayout = findViewById(R.id.coord);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this ,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS},10);

        }
        bounceanime = AnimationUtils.loadAnimation(this, R.anim.bounce);
        moveanime = AnimationUtils.loadAnimation(this, R.anim.down);
        BounceInterpolar interpolator = new BounceInterpolar(0.2, 20);
        bounceanime.setInterpolator(interpolator);
        i2.startAnimation(bounceanime);
        i2.setOnClickListener(v -> i2.startAnimation(bounceanime));

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                mVerificationInProgress = false;
                updatePhone();
                Snackbar.make(coordinatorLayout,"Verification complete",Snackbar.LENGTH_SHORT).show();
                progress.hide();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(OTP.this,"InValid Phone Number",Toast.LENGTH_SHORT).show();
                    progress.hide();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(OTP.this, "Something went wrong...please try again", Toast.LENGTH_SHORT).show();
                    progress.hide();
                }

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                progress.hide();

            }
        };

        b1.setOnClickListener(v -> {

            progress = ProgressDialog.show(OTP.this, "Please Wait..",
                    "Authenticating...", true);
            progress.setCancelable(true);
            number = codePicker.getFullNumberWithPlus();
            if(e1.getText().toString().isEmpty()){
                Snackbar.make(coordinatorLayout,"Enter phone number!",Snackbar.LENGTH_SHORT).show();
                progress.hide();
            }else{
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        number,
                        60,
                        java.util.concurrent.TimeUnit.SECONDS,
                        OTP.this,
                        mCallbacks);   // this wil verify phone number
            }
        });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        updatePhone(); //updation of phone number
                        SharedPreferences preferences = getSharedPreferences("PHONE_NO",MODE_PRIVATE);
                        SharedPreferences.Editor editorPhone = preferences.edit();
                        editorPhone.putString("p_no",e1.getText().toString());
                        editorPhone.apply();

                        startActivity(new Intent(OTP.this,LoginBoth.class));

                        progress.hide();
                        // saving status of first run as false if the task is successful.
                        preferences = getSharedPreferences("FIRST_RUN",MODE_PRIVATE);
                     //   isFirstRun = false;
//                        SharedPreferences.Editor editor = preferences.edit();
//                        editor.putBoolean("checkrunstatus",isFirstRun);
                       // editor.apply();

                        finish();
                    } else {
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            progress.hide();
                            Toast.makeText(OTP.this,"Invalid Verification",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    public void updatePhone(){
        //Save Phone Number
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT, e1.getText().toString());
        editor.apply();
        Toast.makeText(this, "Phone number saved!", Toast.LENGTH_SHORT).show();
        loadPhone();
    }

    public void loadPhone(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "Not Set");
        mcurrentPhone.setText("Your current phone number is : "+text);
    }
}
