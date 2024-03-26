package com.example.clickertest;


import android.util.Log;

public class Repository {

    //Переменные
    private static Repository Repinstance = null;
    private static int Balance;
    private int AddBalanceClick = 1; // +За клик
    private final int[] IncrProgressBar=new int[5]; // Рост картошки
    private int PlantPotato; // Всего выросло картошки
    private int MaxDuration= 50; // Тиков на одну плантацию
    private int AddBalancePotatoes=1; //За одну выросшую картошку
    private final int[] Market={0,0,0,1,0,0}; //Массив с покупками
    private int potatoAllAdd=1; //Множитель картошек за один вырост
    private int CountIncrProgBar ; //

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
    public int[] getIncrProgressBarAll() {
        return IncrProgressBar;
    }
    public int getIncrProgressBar(int position) {
        return IncrProgressBar[position];
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
    public int getAddBalanceClick() {
        return AddBalanceClick;
    }
    public int getSumSlaves(){
        int sum=0;
        for (int j : IncrProgressBar) {
            sum += j;
        }
        return sum;
    }

    //SetterЫ

    public void setBalance(int balance) {
        Balance = balance;
    }
    public void setShovel(int shovel) {
        Market[0]=shovel;
        AddBalanceClick=1+shovel;
    }
    public void setPerchi(int perchi) {
        Market[1]=perchi;
    }
    public void setSlave(int slave) {
        Market[2]=slave;
    }
    public void setBuyPlant(int count){
        Market[3]=count;
    }
    public void setTraktor(int traktor) {
        Market[4]=traktor;
    }
    public void setPlantPotato(int plantPotato) {
        PlantPotato = plantPotato;
    }


    //Increment
    public void IncrBalanceClick(){
        Balance+=AddBalanceClick;
    } //Увеличение баланса при клике
    public void IncrPotatoAllAdd(){
        potatoAllAdd++;
        AddBalancePotatoes*=potatoAllAdd;
    } //+ к зачету картошки в статистике
    public void IncrPotatoAll(){
        Balance+=getAddBalancePotato();
        PlantPotato+=potatoAllAdd;
        CountIncrProgBar=PlantPotato/200;
        if (MaxDuration!=MaxDuration+10*CountIncrProgBar) {
            MaxDuration+=10*CountIncrProgBar;
        }
    }//Когда выросла картошка
    public boolean IncrProgresBar(int position){
        if (getSumSlaves()<Market[2]){
            IncrProgressBar[position]++;
            return true;
        }
        return false;
    }
    public boolean DecrProgresBar(int position){
        if (IncrProgressBar[position]>0) {
            IncrProgressBar[position]--;
            return true;
        }
        return false;
    }
    //Buy item
    private void Zatichka(){

    }
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
                    Runnable[] actions = {
                            ()->AddBalanceClick++,
                            this::Zatichka,
                            this::Zatichka,
                            this::Zatichka,
                            this::IncrPotatoAllAdd,
                    };
                    if (position < actions.length) {
                        actions[position].run();
                    }
                    return true;
                }
            }
            return false;
    }
}
