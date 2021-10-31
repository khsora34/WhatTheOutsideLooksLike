package com.setoncios.mobileappdevelopment.whattheoutsidelookslike;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class HourlyWeatherViewHolder extends RecyclerView.ViewHolder {
    TextView dayTextView;
    TextView timeTextView;
    ImageView imageView;
    TextView temperatureTextView;
    TextView imageDescriptionTextView;

    public HourlyWeatherViewHolder(View itemView) {
        super(itemView);
        this.dayTextView = itemView.findViewById(R.id.hourlyCellDayTextView);
        this.timeTextView = itemView.findViewById(R.id.hourlyCellTimeTextView);
        this.imageView = itemView.findViewById(R.id.hourlyCellImageView);
        this.temperatureTextView = itemView.findViewById(R.id.hourlyCellTemperatureTextView);
        this.imageDescriptionTextView = itemView.findViewById(R.id.hourlyCellImageDescriptionTextView);
    }
}
