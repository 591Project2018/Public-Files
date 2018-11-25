package com.ja.getdevicelocation;

public class WeatherInfo {
    private double temMin;
    private String city;
    private double temMax;
    private String mainWeather;
    private int humid;
    private double pressure;

    public WeatherInfo(String city,double temMin,double temMax,String mainWeather, double pressure, int humid) {
        this.temMin=temMin;
        this.city=city;
        this.temMax=temMax;
        this.mainWeather=mainWeather;
        this.pressure=pressure;
        this.humid=humid;

    }

    public double getTemMin() {
        return temMin;
    }

    public double getTemMax() {
        return temMax;
    }

    public String getCity() {
        return city;
    }

    public String getMainWeather() {
        return mainWeather;
    }

    public double getHumid() {
        return humid;
    }

    public double getPressure() {
        return pressure;
    }




}

