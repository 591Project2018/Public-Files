package com.ja.getdevicelocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherDisplay extends AppCompatActivity {
    private TextView real_weather, real_pressure, location,temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_display);
        Intent intent=getIntent();
        String city=intent.getStringExtra("city");
        String pressure=intent.getStringExtra("pressure");
        String mainWeather=intent.getStringExtra("mainWeather");
        location=findViewById(R.id.location);
        real_weather=findViewById(R.id.weather);
        real_pressure=findViewById(R.id.pressure);
        temp=findViewById(R.id.temp);
        location.setText(city);
        real_weather.setText(mainWeather);
        real_pressure.setText(pressure);
        String tempMax=intent.getStringExtra("temp");
        temp.setText(tempMax);

        location.setOnTouchListener(new OnSwipeTouchListener(WeatherDisplay.this) {
            public void onSwipeTop() {
                Toast.makeText(WeatherDisplay.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Intent intent1=new Intent(WeatherDisplay.this,TestSwipe.class);
                startActivity(intent1);
            }
            public void onSwipeLeft() {
                Toast.makeText(WeatherDisplay.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(WeatherDisplay.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });

    }
}
