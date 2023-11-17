package com.example.srs6_baluev;

public class Weather {
    private String date;
    private String temp;
    private String we;

    public Weather(String date, String temp, String we) {
        this.date = date;
        this.temp = temp;
        this.we = we;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return we;
    }

    public String getTemp() {
        return temp;
    }
}