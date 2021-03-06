package com.example.sai.com;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.sai.myfarmerapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FertilizersDetails extends AppCompatActivity {
    TextView fertilizername,fertilizerprice,fertilizerdesc;
    ImageView toolimg;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton elegantNumberButton;

    String toolId ="";

    FirebaseDatabase database;
    DatabaseReference tools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizers_details);

        database = FirebaseDatabase.getInstance();
        tools = database.getReference("Fertilizers");

        elegantNumberButton = findViewById(R.id.number_btn);
        btnCart = findViewById(R.id.btnCart);
        fertilizerdesc = findViewById(R.id.mfertilizer_description);
        fertilizername = findViewById(R.id.mfertilizer_name);
        fertilizerprice = findViewById(R.id.mfertilizer_price);

        collapsingToolbarLayout = findViewById(R.id.f_collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAdapter);

        if (getIntent()!=null){
            toolId = getIntent().getStringExtra("FertilizerId");

            if (!toolId.isEmpty()){
                getDetailedTool(toolId);
            }
        }
    }

    private void getDetailedTool(String toolId) {
        tools.child(toolId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MyFertilizers tool = dataSnapshot.getValue(MyFertilizers.class);

                collapsingToolbarLayout.setTitle(tool.getName());
                fertilizerprice.setText(tool.getPrice());
                fertilizername.setText(tool.getName());
                fertilizerdesc.setText(tool.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
