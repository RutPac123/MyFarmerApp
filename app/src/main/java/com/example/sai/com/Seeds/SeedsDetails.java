package com.example.sai.com.Seeds;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.sai.Fertilizers.FertilizersDetails;
import com.example.sai.com.Database.Database;
import com.example.sai.com.Model.Order;
import com.example.sai.myfarmerapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SeedsDetails extends AppCompatActivity {
    TextView toolname,toolprice,tooldesc;
    ImageView toolimg;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton elegantNumberButton;

    String seed_id ="";

    FirebaseDatabase database;
    DatabaseReference tools;
    private String[] array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeds_details);

        database = FirebaseDatabase.getInstance();
        tools = database.getReference("Seeds");

        elegantNumberButton = findViewById(R.id.number_btn);
        btnCart = findViewById(R.id.btnCart2);
        tooldesc = findViewById(R.id.mtool_description);
        toolname = findViewById(R.id.mtool_name);
        toolprice = findViewById(R.id.mtool_price);
        toolimg = findViewById(R.id.smimg_tool);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAdapter);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] data = getDetailedTool(seed_id);
                new Database(SeedsDetails.this).addToCart(new Order(
                        seed_id,data[0],elegantNumberButton.getNumber(),
                        data[1],data[2]
                ));
                Toast.makeText(SeedsDetails.this,data[0]+ " added to cart", Toast.LENGTH_SHORT).show();
            }
        });
        if (getIntent()!=null){
            seed_id = getIntent().getStringExtra("SeedId");

            if (!seed_id.isEmpty()){
                getDetailedTool(seed_id);

            }
        }
    }

    private String[] getDetailedTool(String toolId) {
        tools.child(toolId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MySeeds tool = dataSnapshot.getValue(MySeeds.class);
                Picasso.get().load(tool.getImage()).into(toolimg);

                collapsingToolbarLayout.setTitle(tool.getName());
                toolprice.setText(tool.getPrice());
                toolname.setText(tool.getName());
                tooldesc.setText(tool.getDescription());

                array = new String[]{tool.getName(), tool.getPrice(), tool.getDiscount()};

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return array;
    }
}
