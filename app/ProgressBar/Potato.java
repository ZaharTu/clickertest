package com.example.clickertest;

public class Potato {
    private String Name;
    private int Image;
    private int MaxProgress;

    public Potato(String name, int image, int maxProgress) {
        Name = name;
        Image = image;
        MaxProgress = maxProgress;
    }

    public String getName() {
        return Name;
    }

    public int getImage() {
        return Image;
    }

    public int getMaxProgress() {
        return MaxProgress;
    }
}
