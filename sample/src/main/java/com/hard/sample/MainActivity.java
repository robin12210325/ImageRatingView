package com.hard.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hard.imageratingview.ImageRatingView;

public class MainActivity extends AppCompatActivity {

    ImageRatingView sample0;
    ImageRatingView sample1;
    ImageRatingView sample2;
    ImageRatingView sample3;
    ImageRatingView sample4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sample0 = (ImageRatingView) findViewById(R.id.sample0);
        sample1 = (ImageRatingView) findViewById(R.id.sample1);
        sample2 = (ImageRatingView) findViewById(R.id.sample2);
        sample3 = (ImageRatingView) findViewById(R.id.sample3);
        sample4 = (ImageRatingView) findViewById(R.id.sample4);

    }
}
