package com.ja.getdevicelocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SearchInfo extends AppCompatActivity {
    private TextView real_weather, real_pressure, location,temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_info);
        Intent intent = getIntent();
        String city=intent.getStringExtra("city_city");
        String pressure=intent.getStringExtra("city_pressure");
        String mainWeather=intent.getStringExtra("city_mainWeather");
        location=findViewById(R.id.search_location);
        real_weather=findViewById(R.id.search_weather);
        real_pressure=findViewById(R.id.search_pressure);
        temp=findViewById(R.id.search_temp);
        location.setText(city);
        real_weather.setText(mainWeather);
        real_pressure.setText(pressure);
        String tempMax=intent.getStringExtra("city_temp");
        temp.setText(tempMax);

        location.setOnTouchListener(new OnSwipeTouchListener(SearchInfo.this) {
            public void onSwipeTop() {
                Toast.makeText(SearchInfo.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Intent intent1=new Intent(SearchInfo.this,TestSwipe.class);
                startActivity(intent1);
            }
            public void onSwipeLeft() {
                Toast.makeText(SearchInfo.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(SearchInfo.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });

    }

}
