package com.example.sai.com.Seeds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.sai.com.Interface.ItemClickListener;
import com.example.sai.myfarmerapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Seeds extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference seedslist;
    FirebaseRecyclerAdapter<MySeeds,SeedsViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeds);

        firebaseDatabase = FirebaseDatabase.getInstance();
        seedslist = firebaseDatabase.getReference("Seeds");

        recyclerView = findViewById(R.id.seedsitemsview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadSeeds();
    }

    private void loadSeeds() {
        adapter = new FirebaseRecyclerAdapter<MySeeds, SeedsViewHolder>(MySeeds.class, R.layout.seed_item,SeedsViewHolder.class, seedslist) {
            @Override
            protected void populateViewHolder(SeedsViewHolder viewHolder, MySeeds model, int position) {
                viewHolder.toolname.setText(model.getName());
                viewHolder.toolprice.setText(model.getPrice());
                Picasso.get().load(model.getImage()).into(viewHolder.toolimg);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(Seeds.this, SeedsDetails.class);
                        intent.putExtra("SeedId",adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
}
