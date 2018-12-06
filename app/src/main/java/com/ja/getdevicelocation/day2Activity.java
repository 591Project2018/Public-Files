package com.ja.getdevicelocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



    public class day2Activity extends AppCompatActivity {
        private TextView real_weather, real_pressure, location,real_temperature,real_humid,real_tempMax,real_tempMin;
        private String lat,lon;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_weather_display2);
            Intent intent=getIntent();
            String city=intent.getStringExtra("city");
            String pressure=intent.getStringExtra("pressure");
            String mainWeather=intent.getStringExtra("mainWeather");



            ImageView view=findViewById(R.id.WeatherImage2);

            if(mainWeather.contains("Rain")) {
                view.setImageResource(R.drawable.rainy);
            }else if (mainWeather.contains("rain")) {
                view.setImageResource(R.drawable.rainy);
            }else if (mainWeather.contains("snow")) {
                view.setImageResource(R.drawable.snow);
            }else if (mainWeather.contains("Snow")) {
                view.setImageResource(R.drawable.snow);
            } else if (mainWeather.contains("Cloud")) {
                view.setImageResource(R.drawable.cloudy);
            } else if (mainWeather.contains("cloud")) {
                view.setImageResource(R.drawable.cloudy);
            } else if (mainWeather.contains("Clear")) {
                view.setImageResource(R.drawable.sunny);
            } else if (mainWeather.contains("clear")) {
                view.setImageResource(R.drawable.sunny);
            } else if (mainWeather.contains("sun")) {
                view.setImageResource(R.drawable.sunny);
            }else if (mainWeather.contains("fog")) {
                view.setImageResource(R.drawable.fog);
            }else if (mainWeather.contains("haze")) {
                view.setImageResource(R.drawable.fog);
            }else if (mainWeather.contains("Fog")) {
                view.setImageResource(R.drawable.fog);
            }else if (mainWeather.contains("Haze")) {
                view.setImageResource(R.drawable.fog);
            }else{
                view.setImageResource(R.drawable.sunny);
            }






            //calendar.add(calendar.DATE,1);
            //Date tom5=calendar.getTime();
            //String sixthAsString=new SimpleDateFormat("MM-dd").format(tom5);
            //TextView sixth=findViewById(R.id.sixth);
            //sixth.setText(sixthAsString);

            String temperature=intent.getStringExtra("temperature");
            String tempMax=intent.getStringExtra("tempMax");
            String tempMin=intent.getStringExtra("tempMin");
            String humid=intent.getStringExtra("humid");
            location=findViewById(R.id.location2);
            real_weather=findViewById(R.id.weather2);
            real_pressure=findViewById(R.id.pressure2);
            real_temperature=findViewById(R.id.temperature2);
            real_tempMax=findViewById(R.id.tempMax2);
            real_tempMin=findViewById(R.id.tempMin2);
            real_humid=findViewById(R.id.humid2);

            location.setText(city);
            real_weather.setText(mainWeather);
            real_pressure.setText(pressure);
            real_temperature.setText(temperature);
            real_humid.setText(humid);
            real_tempMax.setText(tempMax);
            real_tempMin.setText(tempMin);
            this.lat=intent.getStringExtra("lat");
            this.lon=intent.getStringExtra("lon");



            location.setOnTouchListener(new OnSwipeTouchListener(com.ja.getdevicelocation.day2Activity.this) {
                public void onSwipeTop() {
                    Toast.makeText(com.ja.getdevicelocation.day2Activity.this, "top", Toast.LENGTH_SHORT).show();
                }
                public void onSwipeRight() {
                    ForecastTask forecastTask=new ForecastTask(com.ja.getdevicelocation.day2Activity.this, day3Activity.class);
                    forecastTask.execute(lat,lon);
                    startActivity(forecastTask.intent);
                }
                public void onSwipeLeft() {
                    Toast.makeText(com.ja.getdevicelocation.day2Activity.this, "left", Toast.LENGTH_SHORT).show();
                }
                public void onSwipeBottom() {
                    Toast.makeText(com.ja.getdevicelocation.day2Activity.this, "bottom", Toast.LENGTH_SHORT).show();
                }

            });


        }
    }

