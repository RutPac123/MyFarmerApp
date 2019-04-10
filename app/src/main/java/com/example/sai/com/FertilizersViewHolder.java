package com.example.sai.com;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sai.com.Interface.ItemClickListener;
import com.example.sai.myfarmerapp.R;

public class FertilizersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView fertilizername;
    public TextView fertilizerprice;
    private ItemClickListener itemClickListener;

    public FertilizersViewHolder(@NonNull View itemView) {
        super(itemView);
        fertilizername = itemView.findViewById(R.id.fertilizer_name);
        fertilizerprice = itemView.findViewById(R.id.fertilizer_price);
        itemView.setOnClickListener(this);
    }

    public void setFertilizername(TextView fertilizername) {
        this.fertilizername = fertilizername;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
