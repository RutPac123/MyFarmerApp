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

public class InscDetails extends AppCompatActivity {
    TextView toolname,toolprice,tooldesc;
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
        setContentView(R.layout.activity_insc_details);

        database = FirebaseDatabase.getInstance();
        tools = database.getReference("Pesticides");

        elegantNumberButton = findViewById(R.id.number_btn);
        btnCart = findViewById(R.id.btnCart);
        tooldesc = findViewById(R.id.mtool_description);
        toolname = findViewById(R.id.mtool_name);
        toolprice = findViewById(R.id.mtool_price);
        toolimg = findViewById(R.id.mimg_tool);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAdapter);

        if (getIntent()!=null){
            toolId = getIntent().getStringExtra("ToolId");

            if (!toolId.isEmpty()){
                getDetailedTool(toolId);
            }
        }
    }

    private void getDetailedTool(String toolId) {
        tools.child(toolId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MyInsc tool = dataSnapshot.getValue(MyInsc.class);
                Picasso.get().load(tool.getImage()).into(toolimg);

                collapsingToolbarLayout.setTitle(tool.getName());
                toolprice.setText(tool.getPrice());
                toolname.setText(tool.getName());
                tooldesc.setText(tool.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
