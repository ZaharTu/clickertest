package com.example.clickertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.util.Log;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.clickertest.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;



public class MainActivity extends AppCompatActivity {
    private Repository repository;
    private ActivityMainBinding binding;
    private SetUpPlantClass setUpPlantClass;
    private TextView money;
    private RecyclerView recyclerView;
    private FIleRedactor fileRedactor;
    private ProgressBarAdapter adapter;
    private MediaPlayer mediaPlayer;
    private MyViewModel myViewModel;
    private HandlerX2Btn handlerX2Btn;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        myViewModel= MyViewModel.newInstance(this);
        handlerX2Btn= new HandlerX2Btn(this);
        repository=Repository.newInstance();
        fileRedactor=new FIleRedactor();
        fileRedactor.setContext(this);
        fileRedactor.ReadFile();
        setUpPlantClass = SetUpPlantClass.newInstance(this);
        money=binding.money;
        mediaPlayer=MediaPlayer.create(this,R.raw.digging);
        Intent MainIntent = new Intent(this, MarketActivity.class);
        Animation animPotatoBtn = AnimationUtils.loadAnimation(this, R.anim.main_potato_anim);
        binding.potatobtn.setOnClickListener(v -> {
            binding.potatobtn.startAnimation(animPotatoBtn);
            repository.IncrBalanceClick();
            mediaPlayer.start();
        });
        binding.btnProg.setOnClickListener(v ->
                Snackbar.make(binding.getRoot(),
                        repository.getProg(),
                        Snackbar.LENGTH_SHORT).show());
        binding.btnmarket.setOnClickListener(v -> startActivity(MainIntent));
        binding.SlavesTV.setText(repository.getMarket()[2]+
                "/"+repository.getMarket()[2]+" рабов");
        binding.money.setText(repository.getBalance ()+"$");
        //ADAPTER START
        adapter = new ProgressBarAdapter(this);
        recyclerView = binding.RecyclerPotato;
        recyclerView.setItemAnimator(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        binding.Perchi.setOnClickListener(v-> {
            if (repository.getMarket()[1] > 0) {
                handlerX2Btn.start();
            }else {
                Snackbar.make(this,binding.getRoot(),
                        "Купите перчатки",Snackbar.LENGTH_SHORT).show();
            }
                }
        );
        myViewModel.getLiveData().observe(this, data -> {
            if (data.getStringData()!=null) {
                if (data.getIntData() == 1) {
                    binding.Perchi.setText(data.getStringData());
                } else if (data.getIntData() == 2) {
                    binding.PerchiKD.setText(data.getStringData());
                }
            }else {
                binding.SlavesTV.setText(data.getIntData()+
                        "/"+repository.getMarket()[2]+" рабов");
            }
        });

        //ADAPTER END
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    money.setText(repository.getBalance()+"$");
                });
            }
        },0L,100L);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        binding.Perchi.setText("х2 Клик");
        binding.money.setText(repository.getBalance()+"$");
        int delta=repository.getMarket()[3]-adapter.getItemCount();
        if (delta>0){
            for (int i = 0; i < delta; i++) {
                setUpPlantClass.AddPlant();
            }
        }
        adapter.notifyItemRangeInserted(adapter.getItemCount()-delta, delta);
    }

    @Override
    protected void onPause() {
        super.onPause();
        fileRedactor.WriteFile();
    }

}
