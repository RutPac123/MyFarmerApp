package com.example.sai.com;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.sai.myfarmerapp.R;

public class CardAdapter extends ArrayAdapter<Integer>{

    public CardAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = convertView.findViewById(R.id.img_content);
        imageView.setImageResource(getItem(position));
        return convertView;
    }
}
