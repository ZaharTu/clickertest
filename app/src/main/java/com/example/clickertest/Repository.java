package com.example.clickertest;

public class Repository {
    private static Repository Repinstance = null;
    private static int Balance;
    private int AddBalanceClick = 1;
    private int IncrProgressBar;
    private final int[] MaxDurations={49,103};
    private final int[] AddBalancePotatoes={1,2};
    private final int[] Market= new int[6];
    private final int[] ProgressMas={0,0};
    private Repository() {
        Balance=0;
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
    public int getMaxDuration(int position){
        return MaxDurations[position];
    }
    public int getBalance() {
        return Balance;
    }
    public int getIncrProgressBar() {
        return IncrProgressBar;
    }
    public void setIncrProgressBar(int incrProgressBar) {
        this.IncrProgressBar=incrProgressBar;
    }
    public int getAddBalanceClick() {
        return AddBalanceClick;
    }
    public int[] getMarket() {
        return Market;
    }
    public int getProgress(int position){return ProgressMas[position];}
    public void setProgress(int position,int progress){ProgressMas[position]=progress;}
    public void setBalance(int balance) {
        Balance = balance;
    }
    public void setMarketElem(int position, int count) {
        Market[position]=count;
    }
    public void setAddBalanceClick(int addBalanceClick) {
        AddBalanceClick = addBalanceClick;
    }
    public void IncrAddBalance(){
        AddBalanceClick+=1;
    }
    public void IncrBalanceClick(){
        Balance+=AddBalanceClick;
    }
    public void IncrBalancePotato(int position){
        Balance+=getAddBalancePotato(position);
        ProgressMas[position]=0;
    }
    public boolean BuyItem(int cost,int position){
        if (Balance>=cost){
            Balance-=cost;
            Market[position]++;
            switch (position){
                case 0:
                    IncrAddBalance();
                    break;
                case 1:
                    setIncrProgressBar(Market[0]);
                    break;
            }
            return true;
        }
        return false;
    }
    public int[] getMaxDurations() {
        return MaxDurations;
    }
}
