package com.example.clickertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProgressBarAdapter extends RecyclerView.Adapter<ProgressBarAdapter.MyViewHolder> {
    Context context;
    ArrayList<Potato> potatoArrayList ;
    public ProgressBarAdapter(Context context, ArrayList<Potato> potatoArrayList){
        this.context=context;
        this.potatoArrayList=potatoArrayList;
    }
    @NonNull
    @Override
    public ProgressBarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.mainpotatobar,parent,false);
        return new ProgressBarAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressBarAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(potatoArrayList.get(position).getName());
        holder.imageView.setImageResource(potatoArrayList.get(position).getImage());
        holder.progressBar.setMax(potatoArrayList.get(position).getMaxProgress());
    }

    @Override
    public int getItemCount() {
        return potatoArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tvName;
        ProgressBar progressBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ImageRecyclerBar);
            tvName = itemView.findViewById(R.id.TxtRecyclerBar);
            progressBar = itemView.findViewById(R.id.progressPotato);

        }
    }
}
