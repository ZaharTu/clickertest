package com.example.clickertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import com.example.clickertest.ProgressBar.Potato;
import com.example.clickertest.ProgressBar.ProgressBarAdapter;
import com.example.clickertest.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    private Repository repository;
    private ActivityMainBinding binding;
    private SetUpPotatoClass setUpPotatoClass;
    private ArrayList<Potato> potatoArrayList;
    private TextView money;
    private RecyclerView recyclerView;
    private FIleRedactor fileRedactor;
    private ProgressBarAdapter adapter;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        repository=Repository.newInstance();
        money=binding.money;
        fileRedactor=new FIleRedactor();
        mediaPlayer=MediaPlayer.create(this,R.raw.digging);
        Intent MainIntent = new Intent(this, MarketActivity.class);
        binding.potatobtn.setOnClickListener(v -> {
            repository.IncrBalanceClick();
            mediaPlayer.start();
        });
        binding.btnmarket.setOnClickListener(v -> startActivity(MainIntent));
        fileRedactor.setContext(this);
        fileRedactor.ReadFile();
        setUpPotatoClass= SetUpPotatoClass.newInstance(this);
        potatoArrayList=setUpPotatoClass.getPotatoArrayList();
        binding.money.setText(repository.getBalance()+"$");
        //ADAPTER START
        adapter = new ProgressBarAdapter(this,potatoArrayList);
        recyclerView = binding.RecyclerPotato;
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //ADAPTER END
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> money.setText(repository.getBalance()+"$"));
            }
        },0L,100L);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.money.setText(+repository.getBalance()+"$");
        if (repository.isFlagBuySlave()){
            repository.AddMaxDurAndProg();
            potatoArrayList.add(0,setUpPotatoClass.AddFirstPotato());
            adapter.setPotatoArrayList(potatoArrayList);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        fileRedactor.WriteFile();
    }
}
