package com.legendsoftware.kelimekarti;

import java.io.Serializable;

/**
 * Created by Uzeyir on 5.1.2016.
 */
public class Word implements Serializable {
    private String sozcuk;
    private String anlam;
    public Word(String x,String y){
        sozcuk=x;
        anlam=y;
    }
    public void setSozcuk(String sozcuk){
        this.sozcuk = sozcuk;
    }
    public void setAnlam(String anlam)
    {
        this.anlam=anlam;
    }
    public String getSozcuk(){
        return this.sozcuk;
    }
    public String getAnlam(){
        return this.anlam;
    }
}
