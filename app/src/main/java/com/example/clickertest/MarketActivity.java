package com.example.clickertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.clickertest.databinding.ActivityMarketBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MarketActivity extends AppCompatActivity  {
protected ActivityMarketBinding binding;
private SetUpItemClass setUpItemClass;
private ArrayList<Item> itemArrayList;
private boolean flag;
private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMarketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = Repository.newInstance();
        setUpItemClass = SetUpItemClass.newInstance(this);
        binding.blnMarket.setText(""+repository.getBalance()+"$");
        //INTENT
        Intent MarketIntent = new Intent(this, MainActivity.class);
        //INTENT END

        //ITEM_ARRAY_LIST
        itemArrayList=setUpItemClass.getItemArrayList();
        //ITEM_ARRAY_LIST END

        //ADAPTER_CREATE
        IM_adapter adapter = new IM_adapter(this,itemArrayList);
        RecyclerView recyclerView = binding.rv1;
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnButtonClickListener(position -> {
            flag = repository.BuyItem(itemArrayList.get(position).getCost(),position);
            if(flag){setUpItemClass.incrCountBuy(position);}
            binding.blnMarket.setText(repository.getBalance()+"$");
            adapter.notifyItemChanged(position);
        });
        //ADAPTER_CREATE END
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> binding.blnMarket.setText(repository.getBalance()+"$"));
            }
        },0L,100L);
    }
}
