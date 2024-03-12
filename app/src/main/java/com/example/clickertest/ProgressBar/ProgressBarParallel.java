package com.example.clickertest.ProgressBar;


import android.widget.Button;
import android.widget.ProgressBar;

import com.example.clickertest.Repository;

public class ProgressBarParallel extends Thread {
    int MaxDur;
    int position;
    Repository repository = Repository.newInstance();
    ProgressItem progressItem;
    ProgressBar progressBar;
    Button btn;

    public ProgressBarParallel(ProgressItem progressItem, Button button, int position) {
        this.position = position;
        this.progressItem = progressItem;
        this.btn = button;
        progressBar = progressItem.getProgressBar();
        MaxDur = progressItem.getMaxProgress();
        progressBar.setMax(MaxDur);
    }

    @Override
    public void run() {
        for (int i = 0; i < MaxDur; i++) {
            if (repository.getProgress(position) >= MaxDur - 3) {
                repository.IncrBalancePotato(position);
                i = 0;
            } else {
                if (repository.IncrOrNot(position)) {
                    progressBar.setProgress(repository.getProgress(position));
                    i = repository.getProgress(position);
                } else {
                    progressBar.setProgress(i*repository.getIncrProgressBar());
                    repository.setProgress(position, i*repository.getIncrProgressBar());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
