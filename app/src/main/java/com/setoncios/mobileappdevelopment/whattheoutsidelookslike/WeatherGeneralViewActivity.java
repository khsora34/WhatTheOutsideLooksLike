package com.setoncios.mobileappdevelopment.whattheoutsidelookslike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class WeatherGeneralViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private HourlyWeatherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.recyclerView = findViewById(R.id.hourlyWeatherRecyclerView);

        this.adapter = new HourlyWeatherAdapter();
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Runnable runnable = new WeatherRunnable(41.8675766, -87.616232, DegreeUnit.celsius, this);
        new Thread(runnable).start();
    }
}