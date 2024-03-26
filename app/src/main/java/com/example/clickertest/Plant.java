package com.example.clickertest;


public class Plant {
    private String Name;
    private int Image;
    private int prog;
    private int Slave;
    public Plant(String name, int image) {
        Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }
    public int getImage() {
        return Image;
    }
    public int getProg() {
        return prog;
    }
    public int getSlave() {
        return Slave;
    }

    public void setProg(int prog) {
        this.prog = prog;
    }
    public void IncrSlave() {
        Slave++;
    }
    public void DincrSlave() {
        if (Slave>0) Slave--;
    }

}
