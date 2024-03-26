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
    Handler handler;
    Plant plant;
    ActivityMainBinding binding;
    private int[] addProgArray; // массив для хранения значений AddProg

    public ProgressBarAdapter(Context context, ArrayList<Plant> plantArrayList,
                              ActivityMainBinding binding){
        this.context=context;
        this.plantArrayList = plantArrayList;
        this.handler=new Handler();
        this.binding=binding;
    }
    public void handleButtonClick(int position, ProgressBarAdapter.ButtonType buttonType) {
        if (buttonType == ProgressBarAdapter.ButtonType.PLUS) {
            if (repository.IncrProgresBar(position)) {
                plantArrayList.get(position).IncrSlave();
                notifyDataSetChanged();
            }
        } else if (buttonType == ProgressBarAdapter.ButtonType.MINUS) {
            if (repository.DecrProgresBar(position)) {
                plantArrayList.get(position).DincrSlave();
                notifyDataSetChanged();
            }
        }
    }
    public void updateSlavesTextView(String text) {
        if (binding != null) {
            binding.SlavesTV.setText(text);
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
        String name = plant.getName();
        holder.tvName.setText(name);
        holder.imageView.setImageResource(plant.getImage());
        holder.progressBar.setMax(repository.getMaxDuration());
        addProgArray=repository.getIncrProgressBarAll();
        new Thread(() -> {
                    while (true) {
                        int progress = plant.getProg() + addProgArray[position];
                        if (progress < repository.getMaxDuration()) {
                            plant.setProg(progress);
                            holder.progressBar.setProgress(progress);
                        } else if (progress >= repository.getMaxDuration()) {
                            holder.progressBar.setMax(repository.getMaxDuration());
                            holder.progressBar.setProgress(0);
                            repository.IncrPotatoAll();
                            plant.setProg(0);
                            addProgArray[position] = repository.getIncrProgressBar(position);
                        }
                        try {

                            Thread.sleep(100); // Пауза в 100 миллисекунд
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
        }).start();
        holder.buttonPlus.setOnClickListener(view -> {
            handleButtonClick(position, ButtonType.PLUS);
            updateSlavesTextView(repository.getSumSlaves() + "/" + repository.getMarket()[2] + " рабов");
        });
        holder.buttonMinus.setOnClickListener(view -> {
            handleButtonClick(position, ButtonType.MINUS);
            updateSlavesTextView(repository.getSumSlaves() + "/" + repository.getMarket()[2] + " рабов");
        });
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
