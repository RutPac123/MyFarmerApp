package com.example.sai.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.sai.com.Interface.ItemClickListener;
import com.example.sai.myfarmerapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Fertilizers extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference fertilizerslist;
    FirebaseRecyclerAdapter<MyFertilizers,FertilizersViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizers);

        firebaseDatabase = FirebaseDatabase.getInstance();
        fertilizerslist = firebaseDatabase.getReference("Fertilizers");

        recyclerView = findViewById(R.id.fertilizersitemsview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadFertilizers();
    }

    private void loadFertilizers() {
        adapter = new FirebaseRecyclerAdapter<MyFertilizers, FertilizersViewHolder>(MyFertilizers.class,R.layout.fertilizer_item,FertilizersViewHolder.class,fertilizerslist) {
            @Override
            protected void populateViewHolder(FertilizersViewHolder viewHolder, MyFertilizers model, int position) {
                viewHolder.fertilizername.setText(model.getName());
                viewHolder.fertilizerprice.setText(model.getPrice());

                final MyFertilizers fertilizerClicked = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(Fertilizers.this,FertilizersDetails.class);
                        intent.putExtra("FertilizerId",adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
}
