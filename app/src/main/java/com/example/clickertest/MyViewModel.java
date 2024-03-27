package com.example.clickertest;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public class MyViewModel extends ViewModel {
    private static MyViewModel instance = null;
    public static class Data {
        private String stringData;
        private int intData;

        public Data(String stringData, int intData) {
            this.stringData = stringData;
            this.intData = intData;
        }
        public Data(int intData) {
            this.intData = intData;
        }
        public String getStringData() {
            return stringData;
        }

        public int getIntData() {
            return intData;
        }
    }


    public static MyViewModel newInstance(Context context) {
        if (instance == null) {
            instance = new ViewModelProvider((ViewModelStoreOwner) context).get(MyViewModel.class);
        }
        return instance;
    }
    private MutableLiveData<Data> liveData = new MutableLiveData<>();

    public MutableLiveData<Data> getLiveData() {
        return liveData;
    }

    public void updateData(Data data) {
        liveData.setValue(data);
    }
}
