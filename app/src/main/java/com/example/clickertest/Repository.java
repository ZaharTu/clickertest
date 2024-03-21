package com.example.clickertest;



public class Repository {

    //Переменные
    private static Repository Repinstance = null;
    private static int Balance;
    private int AddBalanceClick = 1; // +За клик
    private int IncrProgressBar; // Рост картошки
    private int PlantPotato=0; // Всего выросло картошки
    private int MaxDuration=50; // Тиков на одну картошку
    private int AddBalancePotatoes=1; //За одну выросшую картошку
    private final int[] Market= new int[6]; //Массив с покупками
    private int[] ProgressMas={0}; //Массив прогрессбаров
    private boolean flagBuySlave;
    private int MaxSlave;
    private int potatoAllAdd=1; //Множитель картошек за один вырост
    private int SlavesBefore; //

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
    public boolean isFlagBuySlave() {
        return flagBuySlave;
    }
    public int getBalance() {
        return Balance;
    }
    public int getIncrProgressBar() {
        return IncrProgressBar;
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
        return "Желтой картошки "+PlantPotato;
    }
    public int getPlantPotato() {
        return PlantPotato;
    }

    //SetterЫ
    public void setMaxSlave(int maxSlave) {
        MaxSlave = maxSlave;
        Market[3]=(maxSlave/5)-1;
    }
    public void setSlavesBefore(int slavesBefore) {
        if (SlavesBefore==0) Market[2]=slavesBefore;
        SlavesBefore = slavesBefore;
    }
    public void setProgress(int position, int progress){ProgressMas[position]=progress;}
    public void setBalance(int balance) {
        Balance = balance;
    }
    public void setIncrProgressBar(int incrProgressBar) {
        this.IncrProgressBar=incrProgressBar;
        Market[1]=incrProgressBar;
    }
    public void setAddBalanceClick(int addBalanceClick) {
        AddBalanceClick = addBalanceClick;
        Market[0]=addBalanceClick-1;
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
    public void IncrPotatoAll(int position){
        Balance+=getAddBalancePotato();
        ProgressMas[position]=0;
        PlantPotato+=potatoAllAdd;
        MaxDuration=MaxDuration*((PlantPotato/200)+1);
    } //Когда выросла картошка

    //Buy item
    public void AddMaxDurAndProg(){
        int[] newProg= new int[ProgressMas.length+1];
        newProg[0]=0;
        System.arraycopy(ProgressMas,0,newProg,1,ProgressMas.length);
        ProgressMas=newProg;
        flagBuySlave=false;
    } //если купили раба + к массиву прогресса
    public boolean BuyItem(int cost,int position){
        if (Balance>=cost){
            if (position==2 && Market[2]>=MaxSlave) return false;
            else {
                Balance-=cost;
                Market[position]++;
            } //TODO поправить покупку раба
            Runnable[] actions = {
                    ()->AddBalanceClick++,
                    ()->setIncrProgressBar(Market[1]),
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
