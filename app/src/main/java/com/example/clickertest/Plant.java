package com.example.clickertest;


public class Plant {
    private String Name;
    private int Image;
    private int prog;
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

    public void setProg(int prog) {
        this.prog = prog;
    }
}
