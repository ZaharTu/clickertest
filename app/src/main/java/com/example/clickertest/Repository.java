package com.example.clickertest;



public class Repository {
    private static Repository Repinstance = null;
    private static int Balance;
    private int AddBalanceClick = 1;
    private int IncrProgressBar;
    private int[] MaxDurations={49,200};
    private int[] AddBalancePotatoes={1,5};
    private final int[] Market= new int[6];
    private int[] ProgressMas={0,0};
    private boolean flagBuySlave;
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
    public int getMaxDuration(int position){
        return MaxDurations[position];
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
    public void setProgress(int position,int progress){ProgressMas[position]=progress;}
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
            Balance-=cost;
            Market[position]++;
            Runnable[] actions = {
                    this::IncrAddBalance,
                    ()->setIncrProgressBar(Market[0]),
                    ()->flagBuySlave=true,
            };
            if (position < actions.length) {
                actions[position].run();
            }
            return true;
        }
        return false;
    }

}
