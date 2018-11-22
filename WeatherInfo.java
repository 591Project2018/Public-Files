
public class WeatherInfo {
double temMin;
String city;
double temMax;
String mainWeather;
public WeatherInfo(String city,double temMin,double temMax,String mainWeather) {
	this.temMin=temMin;
	this.city=city;
	this.temMax=temMax;
	this.mainWeather=mainWeather;
	
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




}
