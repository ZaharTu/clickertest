package com.example.clickertest;


import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SetUpItemClass extends AppCompatActivity {
    private static SetUpItemClass SetInstence = null;
    protected Context mcontext;
    private ArrayList<Item> itemArrayList ;
    protected int[] itemsCountBuy;
    protected int[] itemsCost;
    protected String[] itemsName;
    protected String[] itemsAbout;
    protected String[] itemsHint;
    private Repository repository= Repository.newInstance();
    int[] itemsImage = {R.drawable.shovel, R.drawable.perchi,
            R.drawable.negr, R.drawable.plant,
            R.drawable.traktor,R.drawable.village};
    private SetUpItemClass(Context context){
        mcontext=context;
        itemArrayList= new ArrayList<>();
            itemsCountBuy = repository.getMarket();
            itemsCost=mcontext.getResources().getIntArray(R.array.MarketCost);
            itemsName=mcontext.getResources().getStringArray(R.array.MarketName);
            itemsAbout=mcontext.getResources().getStringArray(R.array.MarketAbout);
            itemsHint=mcontext.getResources().getStringArray(R.array.MarketHint);
        for (int i = 0; i < itemsImage.length; i++) {
            itemArrayList.add(new Item(itemsName[i],
                    itemsAbout[i],
                    itemsHint[i],
                    itemsCost[i],
                    itemsImage[i],
                    itemsCountBuy[i]));
        }
    }
    public static SetUpItemClass newInstance(Context context) {
        if (SetInstence == null) {
            SetInstence = new SetUpItemClass(context);
        }
        return SetInstence;
    }
    private void incrCountBuyList(int position){
        int count=itemArrayList.get(position).getCountBuy()+1;
        Item item1 = new Item(itemArrayList.get(position).getNameItem(),
                itemArrayList.get(position).getAboutItem(),
                itemArrayList.get(position).getHintItem(),
                itemArrayList.get(position).getCost(),
                itemArrayList.get(position).getImage(),
                count);
        itemArrayList.set(position,item1);
    }
    public void incrCountBuy(int position){
        incrCountBuyList(position);
    }

    public ArrayList<Item> getItemArrayList() {
        return itemArrayList;
    }

}
