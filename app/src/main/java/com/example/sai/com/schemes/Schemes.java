package com.example.sai.com.schemes;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.sai.myfarmerapp.R;

public class Schemes extends AppCompatActivity {

    private TextView title;
    private Animation up,left;
    private String[] schemesList = {"Soil health card", "Neem coated urea", "Pradhan Mantri krishi Sinchai", "National agricultural market",
            "Rashtriya Krishi Vikas Yojana", "Deen Dayal Upadhyaya Gram Jyoti Yojana", "National Mission for Sustainable Agriculture",
            "Micro Irrigation Scheme", "Agriculture Contingency Plan"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schemes);
        RecyclerView recyclerView = findViewById(R.id.recycle_scheme);
        up = AnimationUtils.loadAnimation(this, R.anim.slideup);
        left = AnimationUtils.loadAnimation(this, R.anim.slideleft);
        title = findViewById(R.id.titshemes);
        title.setVisibility(View.INVISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                title.setVisibility(View.VISIBLE);
                title.startAnimation(up);
            }
        },1000);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(new SchemesAdapter(schemesList));
        recyclerView.startAnimation(left);

    }
}
