package com.example.clickertest;

import android.content.Context;

import java.util.ArrayList;

public class SetUpPotatoClass {
    private static SetUpPotatoClass SetInstence = null;
    private ArrayList<Potato> potatoArrayList ;
    private final Repository repository = Repository.newInstance();
    int potatoImage=R.drawable.yellowpotato;
    String potatoName;
    int[] market;

    public static SetUpPotatoClass newInstance(Context context){
        if (SetInstence == null) {
            SetInstence = new SetUpPotatoClass(context);
        }
        return SetInstence;
    }
    private SetUpPotatoClass(Context context){
        potatoArrayList= new ArrayList<>();
        potatoName=context.getResources().getString(R.string.Potato);
        potatoArrayList.add(new Potato(
                potatoName,
                potatoImage
                )
        );
        market=repository.getMarket();
        for (int i = 0; i < market[2]; i++) {
            potatoArrayList.add(0,new Potato(potatoName,potatoImage));
            repository.AddMaxDurAndProg();
        }
    }
    public Potato AddFirstPotato(){
        return new Potato(potatoName,potatoImage);
    }
    public ArrayList<Potato> getPotatoArrayList() {
        return potatoArrayList;
    }
}
