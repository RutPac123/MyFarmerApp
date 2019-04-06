package com.example.sai.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.sai.myfarmerapp.R;

public class Schemes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schemes);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycle_scheme);
        String[] schemesList = {"Soil health card", "Neem coated urea", "Pradhan Mantri krishi Sinchai", "National agricultural market"};
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SchemesAdapter(schemesList));

    }
}
