package com.example.clickertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;



public class IM_adapter extends RecyclerView.Adapter<IM_adapter.MyViewHolder> {
    Context context;
    ArrayList<Item> itemArrayList;
    String count;
    public IM_adapter(Context context, ArrayList<Item> itemArrayList){
        this.context=context;
        this.itemArrayList=itemArrayList;
    }

    public interface OnButtonClickListener {
        void onButtonClicked(int position);
    }
    private OnButtonClickListener buttonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.buttonClickListener = listener;
    }

    @NonNull
    @Override
    public IM_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.upgradeitem1,parent,false);
        return new IM_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IM_adapter.MyViewHolder holder, int position) {
        holder.tvName.setText(itemArrayList.get(position).getNameItem());
        holder.tvAbout.setText(itemArrayList.get(position).getAboutItem());
        holder.tvCost.setText("$"+itemArrayList.get(position).getCost());
        count= String.valueOf(itemArrayList.get(position).getCountBuy());
        switch (itemArrayList.get(position).getCountBuy()){
            case 1:
                count+=" покупка";
                if (position==3){
                    count+=" из 4";
                }
                holder.tvCount.setText(count);
                break;
            case 2:
            case 3:
            case 4:
                count+=" покупки";
                if (position==3){
                    count+=" из 4";
                }
                holder.tvCount.setText(count);
                break;
            default:
                count+=" покупок";
                if (position==3){
                    count+=" из 4";
                }
                holder.tvCount.setText(count);
                break;
        }
        holder.tvHint.setText(String.valueOf(itemArrayList.get(position).getHintItem()));
        holder.imageView.setImageResource(itemArrayList.get(position).getImage());
        holder.button.setOnClickListener(v -> {
            if (buttonClickListener != null) {
                buttonClickListener.onButtonClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        Button button;
        ImageView imageView;
        TextView tvName,tvAbout, tvCost, tvCount,tvHint;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.ImageItem);
            tvName= itemView.findViewById(R.id.ItemName);
            tvAbout= itemView.findViewById(R.id.ItemAbout);
            tvCost= itemView.findViewById(R.id.ItemCost);
            tvCount= itemView.findViewById(R.id.ItemCount);
            tvHint= itemView.findViewById(R.id.ItemHint);
            button = itemView.findViewById(R.id.btnBuy);
            }
        }
    }

