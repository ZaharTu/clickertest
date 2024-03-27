package com.example.clickertest;


import android.util.Log;

public class Repository {

    //Переменные
    private static Repository Repinstance = null;
    private static int Balance;
    private int AddBalanceClick = 1; // +За клик
    private int PlantPotato; // Всего выросло картошки
    private int MaxDuration= 50; // Тиков на одну плантацию
    private int AddBalancePotatoes=1; //За одну выросшую картошку
    private final int[] Market={0,0,0,1,0,0}; //Массив с покупками
    private int potatoAllAdd=1; //Множитель картошек за один вырост
    private int CountIncrProgBar=1 ; //

    //Singltone
    private Repository() {
        Balance = 0;
    }
    public static Repository newInstance() {
        if (Repinstance == null) {
            Repinstance = new Repository();
        }
        return Repinstance;
    }

    //GetterЫ
    public int getAddBalancePotato(){
        return AddBalancePotatoes;
    }
    public int getMaxDuration(){
        return MaxDuration;
    }
    public int getBalance() {
        return Balance;
    }
    public int[] getMarket() {
        return Market;
    }
    public String getProg(){
        return "Желтой картошки "+PlantPotato;
    }
    public int getPlantPotato() {
        return PlantPotato;
    }

    //SetterЫ

    public void ReadFileSave(int[] fileRead){
        for (int i = 0; i < fileRead.length; i++) {
            int value = fileRead[i];
            Log.d("aboba","i="+i+", value ="+value);
            switch (i){
                case 0:
                    Balance=value;
                    break;
                case 1:
                    PlantPotato=value;
                    break;
                case 2: // лопата
                    Market[0]=value;
                    AddBalanceClick=value+1;
                    break;
                case 4:
                    Market[2]=value;
                    break;// раб
                case 6:
                    Market[4]=value;
                    potatoAllAdd=value;
                    AddBalancePotatoes*=value;
                    break;// трактор
                case 3: // перчатка
                case 5: // плантация
                case 7: // деревня
                    Market[i-2]=fileRead[i];
                    break;
            }
        }
    }


    //Increment
    public void IncrBalanceClick(){
        Balance+=AddBalanceClick;
    } //Увеличение баланса при клике
    public void IncrPotatoAll(){
        Balance+=getAddBalancePotato();
        PlantPotato+=potatoAllAdd;
        CountIncrProgBar=PlantPotato/200;
        if (MaxDuration!=MaxDuration+10*CountIncrProgBar) {
            MaxDuration+=10*CountIncrProgBar;
        }
    }//Когда выросла картошка

    //Buy item
    public void PerchiXing(float mnozh){
        AddBalanceClick*=mnozh;
    }
    public boolean BuyItem(int cost,int position){
            if (Balance>=cost){
                if(position==3 && Market[3]>3){
                    return false;
                }
                else {
                    Balance-=cost;
                    Market[position]++;
                    switch (position){
                        case 0:
                            AddBalanceClick++;
                            break;
                        case 4:
                            potatoAllAdd++;
                            AddBalancePotatoes=1+potatoAllAdd;
                            break;
                    }
                    return true;
                }
            }
            return false;
    }
}
