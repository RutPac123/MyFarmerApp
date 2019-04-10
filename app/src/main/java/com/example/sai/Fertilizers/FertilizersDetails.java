package com.example.sai.Fertilizers;

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
import com.example.sai.com.Database.Database;
import com.example.sai.com.Model.Order;
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
    private ElegantNumberButton elegantNumberBtn;
    MyFertilizers fertilizers;

    String toolId ="";

    FirebaseDatabase database;
    DatabaseReference tools;
    private String[] array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizers_details);

        database = FirebaseDatabase.getInstance();
        tools = database.getReference("Fertilizers");

        elegantNumberBtn = findViewById(R.id.f_number_btn);
        elegantNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FertilizersDetails.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        btnCart = findViewById(R.id.btnCart);
        fertilizerdesc = findViewById(R.id.mfertilizer_description);
        fertilizername = findViewById(R.id.mfertilizer_name);
        fertilizerprice = findViewById(R.id.mfertilizer_price);

        collapsingToolbarLayout = findViewById(R.id.f_collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAdapter);

        toolimg = findViewById(R.id.mimg_tool);

    //    Toast.makeText(this, elegantNumberButton.getNumber(), Toast.LENGTH_SHORT).show();
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] data = getDetailedTool(toolId);
                new Database(FertilizersDetails.this).addToCart(new Order(
                        toolId,data[0],elegantNumberBtn.getNumber(),
                        data[1],data[2]
                ));
                Toast.makeText(FertilizersDetails.this, data[0]+" added to cart", Toast.LENGTH_SHORT).show();
            }
        });

        if (getIntent()!=null){
            toolId = getIntent().getStringExtra("FertilizerId");

            if (!toolId.isEmpty()){
                getDetailedTool(toolId);
            }
        }
    }

    private String[] getDetailedTool(String toolId) {

        tools.child(toolId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MyFertilizers tool = dataSnapshot.getValue(MyFertilizers.class);
                Picasso.get().load(tool.getImage()).into(toolimg);
                collapsingToolbarLayout.setTitle(tool.getName());
                fertilizerprice.setText(tool.getPrice());
                fertilizername.setText(tool.getName());
                fertilizerdesc.setText(tool.getDescription());

               array = new String[]{tool.getName(), tool.getPrice(), tool.getDiscount()};
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return array;
    }
}
