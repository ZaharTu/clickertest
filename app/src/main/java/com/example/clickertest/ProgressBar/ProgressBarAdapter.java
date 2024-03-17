package com.example.clickertest.ProgressBar;

import android.content.Context;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.clickertest.R;
import com.example.clickertest.Repository;


import java.util.ArrayList;


public class ProgressBarAdapter extends RecyclerView.Adapter<ProgressBarAdapter.MyViewHolder> {
    Context context;
    ArrayList<Potato> potatoArrayList ;
    Repository repository = Repository.newInstance();
    Handler handler;

    public ProgressBarAdapter(Context context, ArrayList<Potato> potatoArrayList){
        this.context=context;
        this.potatoArrayList=potatoArrayList;
        this.handler=new Handler();
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
        String name = potatoArrayList.get(position).getName();
        holder.tvName.setText(name);
        holder.imageView.setImageResource(potatoArrayList.get(position).getImage());
        holder.progressBar.setMax(repository.getMaxDuration(position,name));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int progress = repository.getProgress(position)+ repository.getIncrProgressBar(); // Увеличение прогресса на 1
                if (progress <= repository.getMaxDuration(position,name)) {
                    holder.progressBar.setProgress(progress);
                    repository.setProgress(position,progress);
                } else {
                    holder.progressBar.setMax(repository.getMaxDuration(position,name));
                    repository.IncrPotatoAll(potatoArrayList.get(position).getName());
                    holder.progressBar.setProgress(0);
                    repository.IncrBalancePotato(position);
                }
                handler.postDelayed(this, 100);
            }
        }, 100);

    }

    public void setPotatoArrayList(ArrayList<Potato> potatoArrayList) {
        this.potatoArrayList = potatoArrayList;
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
