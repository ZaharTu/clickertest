package com.example.clickertest;


import android.content.Context;
import android.os.Handler;

import com.example.clickertest.MyViewModel.Data;

public class HandlerX2Btn extends Handler {
    private float PerchiCount;
    private float timerPerchi;
    private float timerPerchiKD;
    private boolean flagPerchiKD;
    private  Repository repository;
    private MyViewModel myViewModel;


    public HandlerX2Btn(Context context) {
        repository= Repository.newInstance();
        myViewModel= MyViewModel.newInstance(context);
    }
    public void start(){
        if (!flagPerchiKD) {
            PerchiCount = repository.getMarket()[1];
            flagPerchiKD=true;
                timerPerchi = PerchiCount;
                repository.PerchiXing(2);
                post(new Runnable() {
                    @Override
                    public void run() {
                        if (timerPerchi > 0.1) {
                            timerPerchi -= 0.1;
                            myViewModel.updateData(new Data(
                                    String.format("%.1f", timerPerchi)
                                                    + "/" + PerchiCount,1));
                            postDelayed(this, 100);
                        } else {
                            repository.PerchiXing(0.5F);
                            timerPerchi = 0;
                            myViewModel.updateData(new Data("х2 Клик",1));
                            timerPerchiKD = 10f;
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    if (timerPerchiKD > 0.1) {
                                        myViewModel.updateData(new Data(
                                                        String.format("%.1f", timerPerchiKD)
                                                        + "/" + 10f,2));
                                        timerPerchiKD -= 0.1;
                                        postDelayed(this, 100);
                                    } else {
                                        myViewModel.updateData(new Data(
                                                        0.0 + "/" + 10.0,2));
                                        flagPerchiKD=false;
                                    }
                                }
                            });
                        }
                    }
                });

        }
    }
}
