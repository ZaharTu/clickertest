package com.example.clickertest;

import android.content.Context;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


public class ProgressBarAdapter extends RecyclerView.Adapter<ProgressBarAdapter.MyViewHolder> {
    Context context;
    ArrayList<Plant> plantArrayList;
    Repository repository = Repository.newInstance();
    Handler handler;
    Plant plant;
    private ProgressBarAdapter.OnButtonClickListener buttonClickListener;
    public ProgressBarAdapter(Context context, ArrayList<Plant> plantArrayList){
        this.context=context;
        this.plantArrayList = plantArrayList;
        this.handler=new Handler();
    }
    public interface OnButtonClickListener {
        void onButtonClick(int position, View view);
    }
    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.buttonClickListener = listener;
    }
    @NonNull
    @Override
    public ProgressBarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.main_plant_bar,parent,false);
        return new ProgressBarAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressBarAdapter.MyViewHolder holder, int position) {
        plant=plantArrayList.get(position);
        String name = plant.getName();
        holder.tvName.setText(name);
        holder.imageView.setImageResource(plant.getImage());
        holder.progressBar.setMax(repository.getMaxDuration(position));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int progress =
                        plant.getProg()
                        +
                        repository.getIncrProgressBar(position);

                if (progress <= repository.getMaxDuration(position)) {
                    holder.progressBar.setProgress(progress);
                    plant.setProg(progress);
                } else {
                    holder.progressBar.setProgress(0);
                    holder.progressBar.setMax(repository.getMaxDuration(position));
                    repository.IncrPotatoAll(position);
                }
                handler.postDelayed(this, 100);
            }
        }, 100);
        holder.buttonPlus.setOnClickListener(view -> {
            if (buttonClickListener != null) {
                buttonClickListener.onButtonClick(position, view);
            }
        });
        holder.buttonMinus.setOnClickListener(view -> {
            if (buttonClickListener != null) {
                buttonClickListener.onButtonClick(position, view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plantArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tvName,tvSlaves;
        ProgressBar progressBar;
        Button buttonPlus,buttonMinus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ImageRecyclerBar);
            tvName = itemView.findViewById(R.id.TxtRecyclerBar);
            tvSlaves=itemView.findViewById(R.id.Slaves);
            progressBar = itemView.findViewById(R.id.progressPotato);
            buttonPlus = itemView.findViewById(R.id.plus_button);
            buttonMinus = itemView.findViewById(R.id.minus_button);
        }
    }
}
