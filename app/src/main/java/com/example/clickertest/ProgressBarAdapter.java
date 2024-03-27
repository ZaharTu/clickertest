package com.example.clickertest;

import android.annotation.SuppressLint;
import android.content.Context;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.clickertest.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class ProgressBarAdapter extends RecyclerView.Adapter<ProgressBarAdapter.MyViewHolder> {
    Context context;
    ArrayList<Plant> plantArrayList;
    Repository repository = Repository.newInstance();
    Plant plant;
    SetUpPlantClass setUpPlantClass;
    MyViewModel myViewModel;
    int slavesRep;

    public ProgressBarAdapter(Context context){
        this.context=context;
        setUpPlantClass=SetUpPlantClass.newInstance(context);
        plantArrayList= setUpPlantClass.getPlantArrayList();
        myViewModel=MyViewModel.newInstance(context);
        slavesRep = repository.getMarket()[2];
    }
    public void handleButtonClick(int position, ProgressBarAdapter.ButtonType buttonType) {
        if (buttonType == ProgressBarAdapter.ButtonType.PLUS) {
            if (slavesRep>0 && slavesRep<=repository.getMarket()[2]) {
                plantArrayList.get(position).start();
                plantArrayList.get(position).IncrSlave();
                slavesRep--;
                notifyItemChanged(position);
                myViewModel.updateData(new MyViewModel.Data(slavesRep));
            }
        } else if (buttonType == ProgressBarAdapter.ButtonType.MINUS) {
            if (slavesRep<repository.getMarket()[2] && plantArrayList.get(position).getSlave()>0) {
                slavesRep++;
                plantArrayList.get(position).DincrSlave();
                notifyItemChanged(position);
                myViewModel.updateData(new MyViewModel.Data(slavesRep));
            }
        }
    }
    public enum ButtonType {
        PLUS,
        MINUS
    }
    @NonNull
    @Override
    public ProgressBarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.main_plant_bar,parent,false);
        return new ProgressBarAdapter.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProgressBarAdapter.MyViewHolder holder, int position) {
        plant=plantArrayList.get(position);
        plant.setProgressBar(holder.progressBar);
        String name = plant.getName();
        holder.tvName.setText(name);
        holder.imageView.setImageResource(plant.getImage());
        holder.progressBar.setMax(repository.getMaxDuration());
        holder.buttonPlus.setOnClickListener(view -> handleButtonClick(position, ButtonType.PLUS));
        holder.buttonMinus.setOnClickListener(view -> handleButtonClick(position, ButtonType.MINUS));
        holder.tvSlaves.setText(""+plantArrayList.get(position).getSlave());

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
