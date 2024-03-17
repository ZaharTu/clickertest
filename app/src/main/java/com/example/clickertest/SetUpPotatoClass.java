package com.example.clickertest;

import android.content.Context;

import com.example.clickertest.ProgressBar.Potato;

import java.util.ArrayList;

public class SetUpPotatoClass {
    private static SetUpPotatoClass SetInstence = null;
    private ArrayList<Potato> potatoArrayList ;
    private Context mcontext;
    private final Repository repository = Repository.newInstance();
    int [] potatoImage={R.drawable.yellowpotato};
    String [] potatoName;
    int[] market;

    public static SetUpPotatoClass newInstance(Context context){
        if (SetInstence == null) {
            SetInstence = new SetUpPotatoClass(context);
        }
        return SetInstence;
    }
    private SetUpPotatoClass(Context context){
        mcontext=context;
        potatoArrayList= new ArrayList<>();
        potatoName= new String[]{context.getResources().getString(R.string.Potato)};
        potatoArrayList.add(new Potato(
                potatoName[0],
                potatoImage[0]
                )
        );
        market=repository.getMarket();
        for (int i = 0; i < market[2]; i++) {
            potatoArrayList.add(0,new Potato(potatoName[0],potatoImage[0]));
            repository.AddMaxDurAndProg();
        }
    }
    public Potato AddFirstPotato(){
        return new Potato(potatoName[0],potatoImage[0]);
    }
    public ArrayList<Potato> getPotatoArrayList() {
        return potatoArrayList;
    }
}
