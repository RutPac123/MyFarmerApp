package com.example.sai.com;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sai.com.Interface.ItemClickListener;
import com.example.sai.myfarmerapp.R;

public class SeedsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView toolname;
    public ImageView toolimg;
    public TextView toolprice;
    private ItemClickListener itemClickListener;

    public SeedsViewHolder(@NonNull View itemView) {
        super(itemView);
        toolname = itemView.findViewById(R.id.seed_name);
        toolimg = itemView.findViewById(R.id.seed_img);
        toolprice = itemView.findViewById(R.id.seed_price);
        itemView.setOnClickListener(this);
    }

    public void setToolname(TextView toolname) {
        this.toolname = toolname;
    }

    public void setToolimg(ImageView toolimg) {
        this.toolimg = toolimg;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
