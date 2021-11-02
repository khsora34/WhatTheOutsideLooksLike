package com.setoncios.mobileappdevelopment.whattheoutsidelookslike.weekforecast;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WeekForecastViewHolder extends RecyclerView.ViewHolder {

    TextView dayTextView;
    TextView degreeTextView;
    TextView descriptionTextView;
    TextView precipitationsTextView;
    TextView uvTextView;
    TextView firstDegreeTextView;
    TextView secondDegreeTextView;
    TextView thirdDegreeTextView;
    TextView fourthDegreeTextView;
    ImageView imageView;

    public WeekForecastViewHolder(@NonNull View view) {
        super(view);
        this.dayTextView = view.findViewById(R.id.dailyCellDayTextView);
        this.degreeTextView = view.findViewById(R.id.dailyCellDegreeTextView);
        this.descriptionTextView = view.findViewById(R.id.dailyCellDescriptionTextView);
        this.precipitationsTextView = view.findViewById(R.id.dailyCellPopTextView);
        this.uvTextView = view.findViewById(R.id.dailyCellUVTextView);
        this.firstDegreeTextView = view.findViewById(R.id.dailyCellFirstDegreeTextView);
        this.secondDegreeTextView = view.findViewById(R.id.dailyCellSecondDegreeTextView);
        this.thirdDegreeTextView = view.findViewById(R.id.dailyCellThirdDegreeTextView);
        this.fourthDegreeTextView = view.findViewById(R.id.dailyCellFourthDegreeTextView);
        this.imageView = view.findViewById(R.id.dailyCellWeatherImageView);
    }
}
