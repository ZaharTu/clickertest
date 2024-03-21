package com.example.clickertest;

import android.content.Context;

import java.util.ArrayList;

public class SetUpPlantClass {
    private static SetUpPlantClass SetInstence = null;
    private ArrayList<Plant> plantArrayList;
    private final Repository repository = Repository.newInstance();
    int plantImage=R.drawable.plant;
    String plantName;
    int[] market;
    public static SetUpPlantClass newInstance(Context context){
        if (SetInstence == null) {
            SetInstence = new SetUpPlantClass(context);
        }
        return SetInstence;
    }
    private SetUpPlantClass(Context context){
        plantArrayList = new ArrayList<>();
        plantName=context.getResources().getString(R.string.Plant);
        market=repository.getMarket();
        for (int i = 0; i < market[3]; i++) {
            AddPlant();
        }
        repository.BuyPlantToZero();

    }
    public ArrayList<Plant> AddPlant(){
        plantArrayList.add(new Plant(plantName,plantImage));
        return plantArrayList;
    }
    public ArrayList<Plant> getPlantArrayList() {
        return plantArrayList;
    }
    public ArrayList<Plant> getIncrPlant() {
        return plantArrayList;
    }

}
