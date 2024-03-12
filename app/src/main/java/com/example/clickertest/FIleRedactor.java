package com.example.clickertest;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FIleRedactor {
    private File file;
    private Context mcontext;
    private final String FileName = "Resources.txt";
    private final String BalanceStr = "Баланс";
    private final String AddBalanceStr = "Изменение баланса";
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
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String key = parts[0].trim(); // Ключ (часть до знака "=")
                        String value = parts[1].trim(); // Значение (часть после знака "=")
                        switch (key) {
                            case BalanceStr:
                                repository.setBalance(Integer.parseInt(value));
                                break;
                            case AddBalanceStr:
                                repository.setAddBalanceClick(Integer.parseInt(value));
                                break;
                        }
                        for (int i = 0; i < MarketItems.length; i++) {
                            String item = MarketItems[i];
                            if (key.equals(item)) {
                                if (item.equals("Лопат")){
                                    repository.setIncrProgressBar(Integer.parseInt(value)/2);
                                } else if (item.equals("Перчаток")) {
                                    repository.setAddBalanceClick(Integer.parseInt(value)+1);
                                }
                                repository.setMarketElem(i, Integer.parseInt(value));
                            }
                        }
                    }
                }
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
                oStrWriter.write("\n"+AddBalanceStr+"="+repository.getAddBalanceClick());
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
}
