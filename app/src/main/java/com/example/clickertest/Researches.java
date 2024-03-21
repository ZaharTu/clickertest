package com.example.clickertest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.clickertest.databinding.ActivityMarketBinding;

public class Researches extends AppCompatActivity {
    private ActivityMarketBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMarketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}