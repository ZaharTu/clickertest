package com.example.clickertest;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FIleRedactor { // По-моему это самый простой и эффективный способ сохранения в моем случае
    private final Repository repository = Repository.newInstance();
    private Context mcontext;
    private final String FileName = "Resources.txt";
    private final String BalanceStr = "Баланс";
    private final String AllPotato = "Всего выросло";
    private final String[] MarketItems=
            {"Лопат","Перчаток","Рабов",
            "Плантаций","Тракторов","Деревень"};
    private final int[] fileRead= new int[8];
    public void setContext(Context context) {
        this.mcontext = context;
    }
    public void ReadFile(){
        File directory = mcontext.getFilesDir();
        File file = new File(directory, FileName);
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                int i=0;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String value = parts[1].trim(); // Значение (часть после знака "=")
                        fileRead[i]=Integer.parseInt(value);
                        i++;
                    }
                }
                repository.ReadFileSave(fileRead);
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            WriteFile();
        }
    }
    public void WriteFile(){
        if (mcontext!=null) {
            try {
                FileOutputStream fOut = mcontext.openFileOutput(FileName,Context.MODE_PRIVATE);
                OutputStreamWriter oStrWriter= new OutputStreamWriter(fOut);
                oStrWriter.write(BalanceStr+"="+repository.getBalance());
                oStrWriter.write("\n"+AllPotato+"="+repository.getPlantPotato());
                for (int i = 0; i < repository.getMarket().length; i++) {
                    oStrWriter.write("\n"+MarketItems[i]+" = "+repository.getMarket()[i]);
                }
                oStrWriter.flush();
                oStrWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public int[] getFileRead() {
        return fileRead;
    }
}
