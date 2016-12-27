package com.hard.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hard.imageratingview.ImageRatingView;

public class MainActivity extends AppCompatActivity {

    ImageRatingView imageRatingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageRatingView= (ImageRatingView) findViewById(R.id.image_rating_view);

        imageRatingView.setRating(100);
    }
}
