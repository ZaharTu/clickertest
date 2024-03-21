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
    private File file;
    private Context mcontext;
    private final String FileName = "Resources.txt";
    private final String BalanceStr = "Баланс";
    private final String AllPotato = "Всего выросло";
    private final String[] MarketItems=
            {"Лопат","Перчаток","Рабов",
            "Плантаций","Тракторов","Деревень"};
    Repository repository = Repository.newInstance();
    public void setContext(Context context) {
        this.mcontext = context;
    }
    public void ReadFile(){
        File directory = mcontext.getFilesDir();
        file = new File(directory, FileName);

        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                int i=0;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String value = parts[1].trim(); // Значение (часть после знака "=")
                            Runnable[] actions = {
                                    ()->repository.setBalance(Integer.parseInt(value)),
                                    ()->repository.setPlantPotato(Integer.parseInt(value)),
                                    ()->repository.setAddBalanceClick(Integer.parseInt(value) + 1),
                                    ()->repository.setShovel(Integer.parseInt(value)),
                                    ()->repository.setSlave(Integer.parseInt(value)),
                                    ()->repository.setBuyPlant(Integer.parseInt(value))
                            };
                        if (i < actions.length) {
                            actions[i].run();
                        }
                        i++;
                    }
                }
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            repository.setBuyPlant(1);
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
                //Записывается Баланс и Общий прогресс
                for (int i = 0; i < repository.getMarket().length; i++) {
                    oStrWriter.write("\n"+MarketItems[i]+" = "+repository.getMarket()[i]);
                } /*
                Записываю сколько предметов каждого наименования. Можно записывать просто числа,
                но тогда это будет нечитабельно
                */
                oStrWriter.flush();
                oStrWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
