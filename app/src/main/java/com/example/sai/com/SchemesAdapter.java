package com.example.sai.com;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sai.myfarmerapp.R;

public class SchemesAdapter extends RecyclerView.Adapter<SchemesAdapter.SchemesViewHolder> {
    private String[] data;

    public SchemesAdapter(String[] data){
        this.data = data;
    }

    @NonNull
    @Override
    public SchemesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_scheme, parent, false);
        return new SchemesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchemesViewHolder schemesViewHolder, int i) {
        String head = data[i];
        schemesViewHolder.title.setText(head);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class SchemesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        private Context context;
        public SchemesViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.scheme_title);
            view.setOnClickListener(this);
            view.setClickable(true);
            context = view.getContext();
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(v.getContext(), getLayoutPosition(), Toast.LENGTH_SHORT).show();
            final Intent intent;
            switch (getAdapterPosition()){
                case 0:
                    intent = new Intent(context, WebviewScheme.class);
                    intent.putExtra("url", "https://soilhealth.dac.gov.in");
                    break;
                case 1:
                    intent = new Intent(context, WebviewScheme.class);
                    intent.putExtra("url", "https://www.kribhco.net/urea.html");
                    break;
                case 2:
                    intent = new Intent(context, WebviewScheme.class);
                    intent.putExtra("url", "https://pmksy.gov.in");
                    break;
                case 3:
                    intent = new Intent(context, WebviewScheme.class);
                    intent.putExtra("url", "https://www.enam.gov.in/web/");
                    break;
                default:
                    intent = new Intent(context, WebviewScheme.class);
                    intent.putExtra("url", "http://agriculture.gov.in/");
                    break;
            }
            context.startActivity(intent);
        }
    }
}
