package com.example.sai.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sai.com.Interface.ItemClickListener;
import com.example.sai.myfarmerapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Tools extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference toolslist;
    FirebaseRecyclerAdapter<MyTools,ToolsViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        firebaseDatabase = FirebaseDatabase.getInstance();
        toolslist = firebaseDatabase.getReference("Tools");

        recyclerView = findViewById(R.id.toolsitemsview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadTools();
    }

    private void loadTools() {
         adapter = new FirebaseRecyclerAdapter<MyTools, ToolsViewHolder>(MyTools.class,R.layout.tool_item,ToolsViewHolder.class,toolslist) {
            @Override
            protected void populateViewHolder(ToolsViewHolder viewHolder, MyTools model, int position) {
                viewHolder.toolname.setText(model.getName());
                viewHolder.toolprice.setText(model.getPrice());
                Picasso.get().load(model.getImage()).into(viewHolder.toolimg);

               final MyTools toolClicked = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(Tools.this,ToolsDetails.class);
                        intent.putExtra("ToolId",adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
}
