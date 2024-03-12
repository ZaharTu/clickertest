package com.example.clickertest.ProgressBar;

import com.example.clickertest.Repository;

public class Potato {
    private String Name;
    private int Image;
    private int maxDur;
    private int addBalance;
    Repository repository = Repository.newInstance();
    public Potato(String name, int image,int position) {
        Name = name;
        Image = image;
        maxDur=repository.getMaxDuration(position);
        addBalance=repository.getAddBalancePotato(position);
    }

    public String getName() {
        return Name;
    }

    public int getImage() {
        return Image;
    }

    public int getMaxDur() {
        return maxDur;
    }
}
