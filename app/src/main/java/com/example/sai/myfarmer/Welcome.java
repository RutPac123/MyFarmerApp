package com.example.sai.myfarmer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {

    private Button fbloginbtn;
    private Button customloginbtn;
    private Button signupbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        fbloginbtn = findViewById(R.id.fblogin);
        customloginbtn = findViewById(R.id.custom_login);
        signupbtn = findViewById(R.id.custom_sign_up);

        customloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentlogin = new Intent(Welcome.this,Login.class);
                startActivity(intentlogin);
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsignup = new Intent(Welcome.this,SignUp.class);
                startActivity(intentsignup);
            }
        });
    }
}
