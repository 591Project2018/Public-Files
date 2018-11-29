package WeatherApp;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DailyWeather {

    private double temMin;
    private String city;
    private double temMax;
    private String discript;
    private double humid;
    private double pressure;
    LocalDateTime dtime;

    public DailyWeather(String city,double temMin,double temMax,String discript, double pressure, double humid, LocalDateTime dtime) {
        this.temMin=temMin;
        this.city=city;
        this.temMax=temMax;
        this.discript=discript;
        this.pressure=pressure;
        this.humid=humid;
        this.dtime = dtime;

    }

    public LocalDateTime getDateTime() {  	
    	return dtime;
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

    public String getDiscription() {
        return discript;
    }

    public double getHumid() {
        return humid;
    }

    public double getPressure() {
        return pressure;
    }


}
