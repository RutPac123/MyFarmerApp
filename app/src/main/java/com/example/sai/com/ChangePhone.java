package com.example.sai.com;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sai.myfarmerapp.R;

public class ChangePhone extends AppCompatActivity {
    private EditText editPhone;
    private TextView currentPhone;
    private Button updatePhone;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        editPhone = findViewById(R.id.edit_phone);
        currentPhone = findViewById(R.id.current_phone);
        updatePhone = findViewById(R.id.update_phone);

        loadPhone();
    }

    public void updatePhone(View v){
        //Save Phone Number
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT, editPhone.getText().toString());
        editor.apply();
        loadPhone();
    }

    public void loadPhone(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "Phone Number Not Set");
        currentPhone.setText(text);
    }
}
