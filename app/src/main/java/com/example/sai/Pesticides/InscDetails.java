package com.example.sai.Pesticides;

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
import com.example.sai.com.Seeds.SeedsDetails;
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
    private ElegantNumberButton elegantNumberButton;

    String insc_id ="";

    FirebaseDatabase database;
    DatabaseReference tools;
    private String[] array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insc_details);

        database = FirebaseDatabase.getInstance();
        tools = database.getReference("Pesticides");

        elegantNumberButton = findViewById(R.id.number_btn1);
        btnCart = findViewById(R.id.btnCart);
        tooldesc = findViewById(R.id.mtool_description);
        toolname = findViewById(R.id.mtool_name);
        toolprice = findViewById(R.id.mtool_price);
        toolimg = findViewById(R.id.mimg_tool);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAdapter);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] data = getDetailedTool(insc_id);
                new Database(InscDetails.this).addToCart(new Order(
                        insc_id,data[0],elegantNumberButton.getNumber(),
                        data[1],data[2]
                ));
                Toast.makeText(InscDetails.this, data[0]+" added to cart", Toast.LENGTH_SHORT).show();

            }
        });
        if (getIntent()!=null){
            insc_id = getIntent().getStringExtra("InscId");

            if (!insc_id.isEmpty()){
                getDetailedTool(insc_id);
            }
        }
    }

    private String[] getDetailedTool(String toolId) {
        tools.child(toolId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MyInsc tool = dataSnapshot.getValue(MyInsc.class);
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
