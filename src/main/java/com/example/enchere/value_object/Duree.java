package com.example.enchere.value_object;

public class Duree {
    private int heure;
    private int minute;

    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int toSeconde(){
        return heure*3600+minute*60;
    }
}
