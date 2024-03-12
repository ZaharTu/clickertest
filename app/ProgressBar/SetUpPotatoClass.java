package com.example.clickertest;

import android.content.Context;

import java.util.ArrayList;

public class SetUpPotatoClass {
    private static SetUpPotatoClass SetInstence = null;
    private Context mcontext;
    private ArrayList<Potato> potatoArrayList ;
    int [] potatoImage={R.drawable.yellowpotato,R.drawable.redpotato};
    String [] potatoName;
    int[] MaxProgress={100,1000};
    public static SetUpPotatoClass newInstance(Context context){
        if (SetInstence == null) {
            SetInstence = new SetUpPotatoClass(context);
        }
        return SetInstence;
    }
    private SetUpPotatoClass(Context context){
        mcontext=context;
        potatoArrayList= new ArrayList<>();
        potatoName=context.getResources().getStringArray(R.array.PotatoName);
        for (int i = 0; i < potatoImage.length; i++) {
            potatoArrayList.add(new Potato(
                    potatoName[i],
                    potatoImage[i],
                    MaxProgress[i])
            );
        }
    }

    public ArrayList<Potato> getPotatoArrayList() {
        return potatoArrayList;
    }
}
