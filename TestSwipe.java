package com.ja.getdevicelocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class TestSwipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_swipe);
        //Intent intent = getIntent();
        //String city = intent.getStringExtra("SearchForTheCity");

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.showtext);
        textView.setText("Welcome to our weather app!");


        //ImageView poster_view = (ImageView)findViewById(R.id.imageView); //Assuming an ImgView is there in your layout activity_main
        //poster_view.setImageResource(R.drawable.pens);

    }
}
