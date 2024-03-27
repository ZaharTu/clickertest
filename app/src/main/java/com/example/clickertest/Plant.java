package com.example.clickertest;


import android.widget.ProgressBar;

public class Plant {
    private String Name;
    private int Image;
    private int prog;
    private int Slave;
    private ProgressBar progressBar;
    private int Maxprogress;
    private boolean flagStart;
    Repository repository=Repository.newInstance();
    public Plant(String name, int image) {
        Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }
    public int getImage() {
        return Image;
    }
    public int getSlave() {
        return Slave;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void IncrSlave() {
        Slave++;
    }
    public void DincrSlave() {
        if (Slave>0) Slave--;
    }
    public void start(){
        if (!flagStart) {
            flagStart = true;
            Maxprogress=repository.getMaxDuration();
            new Thread(() -> {
                while (Slave > 0) {
                    int progress = prog + Slave;
                    if (progress < Maxprogress) {
                        prog = progress;
                        progressBar.setProgress(progress);
                    } else {
                        progressBar.setProgress(0);
                        repository.IncrPotatoAll();
                        Maxprogress = repository.getMaxDuration();
                        progressBar.setMax(Maxprogress);
                        prog = 0;
                    }
                    try {

                        Thread.sleep(100); // Пауза в 100 миллисекунд
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                flagStart = false;
            }).start();
        }
    }

}
