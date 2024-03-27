package com.example.clickertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.clickertest.databinding.ActivityMarketBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;



public class MarketActivity extends AppCompatActivity  {
protected ActivityMarketBinding binding;
private SetUpItemClass setUpItemClass;
private ArrayList<Item> itemArrayList;
private Repository repository;
private boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMarketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = Repository.newInstance();
        setUpItemClass = SetUpItemClass.newInstance(this);
        binding.blnMarket.setText(""+repository.getBalance()+"$");

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
            flag=repository.BuyItem(itemArrayList.get(position).getCost(), position);
            if (flag) {
                setUpItemClass.incrCountBuy(position);
                if(position==3){
                    adapter.notifyItemChanged(2);
                }
                adapter.notifyItemChanged(position);
            }else if (position==4 && repository.getMarket()[3]==4) {
                Snackbar.make(this,binding.getRoot(),
                        "Это максимум",
                        Snackbar.LENGTH_SHORT).show();
            }else {
                Snackbar.make(this,binding.getRoot(),
                        "Накопайте ещё картошки,\nвам не хватает денег",
                        Snackbar.LENGTH_SHORT).show();
            }
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
