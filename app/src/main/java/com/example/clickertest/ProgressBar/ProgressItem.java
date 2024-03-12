package com.example.clickertest.ProgressBar;

import android.widget.ProgressBar;

public class ProgressItem {

    private int maxProgress;
    private ProgressBar progressBar;
    public ProgressItem(int maxProgress,ProgressBar progressBar) {
        this.maxProgress = maxProgress;
        this.progressBar = progressBar;
    }

    public int getMaxProgress() {
        return maxProgress;
    }
    public ProgressBar getProgressBar(){return  progressBar;}
}