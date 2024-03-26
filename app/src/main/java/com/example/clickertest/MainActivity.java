package com.example.clickertest;

import androidx.appcompat.app.AppCompatActivity;
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
    private ArrayList<Plant> plantArrayList;
    private TextView money;
    private RecyclerView recyclerView;
    private FIleRedactor fileRedactor;
    private ProgressBarAdapter adapter;
    private MediaPlayer mediaPlayer;
    private final Handler handler=new Handler(Looper.getMainLooper());
    private float PerchiCount;
    private float timerPerchi;
    private float timerPerchiKD;
    private boolean flagPerchiKD;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        repository=Repository.newInstance();
        money=binding.money;
        fileRedactor=new FIleRedactor();
        fileRedactor.setContext(this);
        fileRedactor.ReadFile();
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
        binding.SlavesTV.setText(repository.getSumSlaves()+
                "/"+repository.getMarket()[2]+" рабов");
        setUpPlantClass = SetUpPlantClass.newInstance(this);
        plantArrayList = setUpPlantClass.getPlantArrayList();
        binding.money.setText(repository.getBalance ()+"$");
        //ADAPTER START
        adapter = new ProgressBarAdapter(this, plantArrayList,binding);
        recyclerView = binding.RecyclerPotato;
        recyclerView.setItemAnimator(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        binding.Perchi.setOnClickListener(v -> {
            if (!flagPerchiKD) {
                PerchiCount = repository.getMarket()[1];
                flagPerchiKD=true;
                if (timerPerchi == 0) {
                    timerPerchi = PerchiCount;
                    repository.PerchiXing(2);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (timerPerchi > 0.1) {
                                timerPerchi -= 0.1;
                                binding.Perchi.setText(String.format("%.1f", timerPerchi) + "/" + PerchiCount);
                                handler.postDelayed(this, 100);
                            } else {
                                repository.PerchiXing(0.5F);
                                timerPerchi = 0;
                                binding.Perchi.setText("х2 Клик");
                                timerPerchiKD = 10f;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (timerPerchiKD > 0.1) {
                                            binding.PerchiKD.setText(String.format("%.1f", timerPerchiKD) + "/" + 10f);
                                            timerPerchiKD -= 0.1;
                                            handler.postDelayed(this, 100);
                                        } else {
                                            binding.PerchiKD.setText(0.0 + "/" + 10.0);
                                            flagPerchiKD=false;
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        //ADAPTER END
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> money.setText(repository.getBalance()+"$"));
            }
        },0L,100L);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        binding.money.setText(repository.getBalance()+"$");
        setUpPlantClass = SetUpPlantClass.newInstance(this);
        recyclerView.setAdapter(new ProgressBarAdapter(this,
                setUpPlantClass.getPlantArrayList(),binding));
        binding.SlavesTV.setText(repository.getSumSlaves()
                +"/"+repository.getMarket()[2]+" рабов");
    }

    @Override
    protected void onPause() {
        super.onPause();
        fileRedactor.WriteFile();
    }

}
