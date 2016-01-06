package com.legendsoftware.kelimekarti;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static TextView tx;
    public static List<Word> wordList = new ArrayList<Word>();
    static int i = 0;
    static boolean change = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx = (TextView)findViewById(R.id.textView);
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Word>>(){}.getType();
        String json = appSharedPrefs.getString("wordlist", "");
        wordList= gson.fromJson(json, type);

    }
    public void goster(View view) {
        if(!wordList.isEmpty()){
            if(!change)
            {
                tx.setText(wordList.get(i).getSozcuk());
                change = !change;
            }
            else
            {
                tx.setText(wordList.get(i).getAnlam());
                change = !change;
            }
        }
    }
    public void addWord(View view){
        if(wordList.isEmpty()){
            i=-1;
        }
        AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View icView = inflater.inflate(R.layout.addword, null);
        final EditText en = (EditText)icView.findViewById(R.id.etEng);
        final EditText tr = (EditText)icView.findViewById(R.id.etTur);
        build.setView(icView);
        build.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String enString = en.getText().toString().trim();
                String trString = tr.getText().toString().trim();
                if(enString.equals("")||trString.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Boş bırakmayalım", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    wordList.add(new Word(enString,trString));
                    setTx(++i);
                }
            }
        });
        build.show();
    }
    public void nextWord(View view) {
        if(!wordList.isEmpty())
        {
            if(i<wordList.size()-1)
            {
                setTx(++i);
            }
            else
            {
                i=0;
                setTx(i);
            }
        }
    }
    public void deleteFromList(View view) {
        if(!wordList.isEmpty())
        {
            if (wordList.size() > 1) {
                wordList.remove(i--);
                setTx(wordList.size()-1);
            }
            else
            {
                i=-1;
                wordList.clear();
                tx.setText("Kelimelik boş");
            }
        }
    }
    public void setTx(int a){
        tx.setText(wordList.get(a).getSozcuk());
    }
    @Override
    public void onBackPressed(){
    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
    builder.setTitle("Kelimeliği kaydetmek istiyormusunuz ?");
    builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
        Toast.makeText(MainActivity.this, "Kaydediliyor", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(wordList);
        editor.putString("wordlist",json);
        editor.commit();
        Toast.makeText(MainActivity.this, "Kaydedildi", Toast.LENGTH_SHORT).show();
        System.exit(1);
    }
        });
        builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
        @Override
            public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(MainActivity.this, "İyi günler :)", Toast.LENGTH_SHORT).show();
            System.exit(1);
            }
        });
        builder.show();
    }
}

