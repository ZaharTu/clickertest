package com.example.clickertest;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Repository {
    private static Repository Repinstance = null;
    private static int Balance;
    private int AddBalanceClick = 1;
    private int IncrProgressBar;
    private final int[] PlantPotato={0,0};
    private int[] MaxDurations={49};
    private int[] AddBalancePotatoes={1};
    private final int[] Market= new int[6];
    private int[] ProgressMas={0};
    private boolean flagBuySlave;
    private int MaxSlave;
    private int potatoAllAdd=1;
    private int SlavesBefore;
    private Repository() {
        Balance = 0;
    }
    public static Repository newInstance() {
        if (Repinstance == null) {
            Repinstance = new Repository();
        }
        return Repinstance;
    }
    public int getAddBalancePotato(int position){
        return AddBalancePotatoes[position];
    }
    public int getMaxDuration(int position, String name){
        if(name.equals("Жёлтая картошка"))
            return MaxDurations[position]*((PlantPotato[0]/200)+1);
        return 150;
    }
    public boolean isFlagBuySlave() {
        return flagBuySlave;
    }
    public int getBalance() {
        return Balance;
    }
    public int getIncrProgressBar() {
        return IncrProgressBar;
    }
    public int getAddBalanceClick() {
        return AddBalanceClick;
    }
    public int[] getMarket() {
        return Market;
    }
    public int getProgress(int position){return ProgressMas[position];}
    public int getMaxSlave() {
        return MaxSlave;
    }
    public int getSlavesBefore() {
        return SlavesBefore;
    }
    public String getProg(){
        return "Желтой картошки "+PlantPotato[0]+"\nКрасной картошки "+PlantPotato[1];
    }
    public void setMaxSlave(int maxSlave) {
        MaxSlave = maxSlave;
    }
    public void setSlavesBefore(int slavesBefore) {
        SlavesBefore = slavesBefore;
    }
    public void setProgress(int position, int progress){ProgressMas[position]=progress;}
    public void setBalance(int balance) {
        Balance = balance;
    }
    public void setMarketElem(int position, int count) {
        Market[position]=count;
    }
    public void setIncrProgressBar(int incrProgressBar) {
        this.IncrProgressBar=incrProgressBar;
    }
    public void setAddBalanceClick(int addBalanceClick) {
        AddBalanceClick = addBalanceClick;
    }
    public void IncrBalanceClick(){
        Balance+=AddBalanceClick;
    }
    public void IncrPotatoAllAdd(){
        potatoAllAdd++;
        for (int i = 0; i < AddBalancePotatoes.length; i++) {
            AddBalancePotatoes[i]*=potatoAllAdd;
        }
    }
    public void IncrPotatoAll(String name){
        if(name.equals("Жёлтая картошка")) PlantPotato[0]+=potatoAllAdd;
        else if (name.equals("Красная картошка")) PlantPotato[1]+=potatoAllAdd;
    }
    public void IncrBalancePotato(int position){
        Balance+=getAddBalancePotato(position);
        ProgressMas[position]=0;
    }
    public void AddMaxDurAndProg(){
        int[] newDur= new int[MaxDurations.length+1];
        int[] newProg= new int[ProgressMas.length+1];
        int[] newAddBalance= new int[AddBalancePotatoes.length+1];
        newDur[0]=MaxDurations[0];
        newAddBalance[0]=AddBalancePotatoes[0];
        newProg[0]=0;
        System.arraycopy(MaxDurations,0,newDur,1,MaxDurations.length);
        System.arraycopy(AddBalancePotatoes,0,newAddBalance,1,AddBalancePotatoes.length);
        System.arraycopy(ProgressMas,0,newProg,1,ProgressMas.length);
        MaxDurations=newDur;
        ProgressMas=newProg;
        AddBalancePotatoes=newAddBalance;
        flagBuySlave=false;
    }
    public boolean BuyItem(int cost,int position){
        if (Balance>=cost){
            if (position==2 && Market[2]>=MaxSlave) return false;
            else {
                Balance-=cost;
                Market[position]++;
            }
            Runnable[] actions = {
                    ()->AddBalanceClick++,
                    ()->setIncrProgressBar(Market[0]),
                    ()-> flagBuySlave=true,
                    ()->MaxSlave+=5,
                    this::IncrPotatoAllAdd,
            };
            if (position < actions.length) {
                actions[position].run();
            }
            return true;
        }
        return false;
    }
}
