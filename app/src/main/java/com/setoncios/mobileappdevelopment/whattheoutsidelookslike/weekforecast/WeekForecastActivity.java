package com.setoncios.mobileappdevelopment.whattheoutsidelookslike.weekforecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.dtos.DegreeUnit;
import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.R;
import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.dtos.DailyWeatherInfoDTO;

public class WeekForecastActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private WeekForecastAdapter adapter;

    private DailyWeatherInfoDTO[] list;
    private DegreeUnit degreeUnit;
    private long timezoneOffset;
    private String locationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_forecast);
        this.recyclerView = this.findViewById(R.id.forecastRecyclerView);
        this.loadFromIntent();
        this.adapter = new WeekForecastAdapter(this.list, this.degreeUnit, this);
        this.adapter.setTimezoneOffset(this.timezoneOffset);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.setTitle(this.locationName);
    }

    private void loadFromIntent() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        if (intent.hasExtra(getString(R.string.intent_timezone_offset))) {
            this.timezoneOffset = intent.getLongExtra(getString(R.string.intent_timezone_offset), 0);
        }
        if (intent.hasExtra(getString(R.string.intent_list))) {
            this.list = (DailyWeatherInfoDTO[]) intent.getSerializableExtra(getString(R.string.intent_list));
        }
        if (intent.hasExtra(getString(R.string.intent_location_name))) {
            this.locationName = intent.getStringExtra(getString(R.string.intent_location_name));
        }
        if (intent.hasExtra(getString(R.string.intent_degree_unit))) {
            this.degreeUnit = (DegreeUnit) intent.getSerializableExtra(getString(R.string.intent_degree_unit));
        }
    }
}