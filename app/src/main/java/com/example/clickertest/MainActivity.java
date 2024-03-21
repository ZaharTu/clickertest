package com.example.clickertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
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
        fileRedactor.setContext(this);
        fileRedactor.ReadFile();
        setUpPlantClass = SetUpPlantClass.newInstance(this);
        plantArrayList = setUpPlantClass.getPlantArrayList();
        binding.money.setText(repository.getBalance()+"$");
        //ADAPTER START
        adapter = new ProgressBarAdapter(this, plantArrayList);
        recyclerView = binding.RecyclerPotato;
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnButtonClickListener(((position, view) -> {
            if (view.getId()==R.id.plus_button){

            } else if (view.getId() == R.id.minus_button) {

            }
        }));
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
        if (repository.getBuyPlant()>0){
            for (int i = 0; i < repository.getBuyPlant(); i++) {
                setUpPlantClass.AddPlant();
            }
            recyclerView.setAdapter(new ProgressBarAdapter(this,
                    setUpPlantClass.getPlantArrayList()));
            repository.BuyPlantToZero();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        fileRedactor.WriteFile();
    }
}
