package com.example.clickertest;

import android.util.Log;

public class Repository {

    //Переменные
    private static Repository Repinstance = null;
    private static int Balance;
    private int AddBalanceClick = 1; // +За клик
    private final int[] IncrProgressBar=new int[5]; // Рост картошки
    private int PlantPotato; // Всего выросло картошки
    private final int[] MaxDuration= new int[]{51, 52, 53, 54,55}; // Тиков на одну картошку
    private int AddBalancePotatoes=1; //За одну выросшую картошку
    private final int[] Market= new int[6]; //Массив с покупками
    private final int[] ProgressMas=new int[5]; //Массив прогрессбаров
    private int SlaveAll;
    private final int[] SlavesOnPlant = new  int[5];
    private int potatoAllAdd=1; //Множитель картошек за один вырост
    private int BuyPlant ; //

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
    public int getMaxDuration(int position){
        return MaxDuration[position];
    }
    public int getBalance() {
        return Balance;
    }
    public int getIncrProgressBar(int position) {
        return IncrProgressBar[position];
    }
    public int[] getMarket() {
        return Market;
    }
    public int getBuyPlant() {
        return BuyPlant;
    }
    public String getProg(){
        return "Желтой картошки "+PlantPotato;
    }
    public int getPlantPotato() {
        return PlantPotato;
    }

    public int getSlavesOnPlant(int position) {
        return SlavesOnPlant[position];
    }
    //SetterЫ

    public void setBalance(int balance) {
        Balance = balance;
    }
    public void setShovel(int shovel) {
        Market[1]=shovel;
    }
    public void setAddBalanceClick(int addBalanceClick) {
        AddBalanceClick = addBalanceClick;
        Market[0]=addBalanceClick-1;
    }
    public void setPlantPotato(int plantPotato) {
        PlantPotato = plantPotato;
    }
    public void setSlave(int slave){
        SlaveAll=slave;
    }
    public void BuyPlantToZero(){
        BuyPlant=0;
    }
    public void setBuyPlant(int count){
        BuyPlant=count;
        Market[3]=count;
    }

    //Increment
    public void IncrBalanceClick(){
        Balance+=AddBalanceClick;
    } //Увеличение баланса при клике
    public void IncrPotatoAllAdd(){
        potatoAllAdd++;
        AddBalancePotatoes*=potatoAllAdd;
    } //+ к зачету картошки в статистике
    public void IncrPotatoAll(int position){
        Balance+=getAddBalancePotato();
        ProgressMas[position]=0;
        PlantPotato+=potatoAllAdd;
        if (MaxDuration[position]!=MaxDuration[position]*((PlantPotato/200)+1)) {
            MaxDuration[position]=MaxDuration[position]*((PlantPotato/200)+1);
        }
    } //Когда выросла картошка
    public void IncrSlavesOnPlant(int position){
        SlavesOnPlant[position]++;
    }

    //Buy item
    public void BuyingPlant(){
        BuyPlant ++;
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
                            ()->notify(),
                            ()->SlaveAll++,
                            this::BuyingPlant,
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
