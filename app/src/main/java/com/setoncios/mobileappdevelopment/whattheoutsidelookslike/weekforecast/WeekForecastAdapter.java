package com.setoncios.mobileappdevelopment.whattheoutsidelookslike.weekforecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.DegreeUnit;
import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.R;
import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.StringExtension;
import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.dtos.DailyWeatherInfoDTO;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class WeekForecastAdapter extends RecyclerView.Adapter<WeekForecastViewHolder> {
    private DailyWeatherInfoDTO[] list;
    private long timezoneOffset = 0;
    private DegreeUnit degreeUnit;
    private Context context;

    public WeekForecastAdapter(DailyWeatherInfoDTO[] list, DegreeUnit degreeUnit, Context context) {
        this.list = list;
        this.degreeUnit = degreeUnit;
        this.context = context;
    }

    @NonNull
    @Override
    public WeekForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_cell, parent, false);
        return new WeekForecastViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekForecastViewHolder holder, int position) {
        DailyWeatherInfoDTO dto = list[position];
        holder.dayTextView.setText(getLocalDate(dto.getDt(), "eeee, M/dd"));
        holder.degreeTextView.setText(String.format(context.getString(R.string.daily_degree_comparation), (int)dto.getTemp().getMax(), this.degreeUnit.getViewValue(), (int)dto.getTemp().getMin(), this.degreeUnit.getViewValue()));
        holder.precipitationsTextView.setText(String.format(this.context.getString(R.string.daily_precipitation), dto.getPop()));
        holder.uvTextView.setText(String.format(this.context.getString(R.string.main_uv_index), (int)dto.getUvi()));
        holder.firstDegreeTextView.setText(String.format(this.context.getString(R.string.main_default_temperature), (int)dto.getTemp().getMorn(), this.degreeUnit.getViewValue()));
        holder.secondDegreeTextView.setText(String.format(this.context.getString(R.string.main_default_temperature), (int)dto.getTemp().getDay(), this.degreeUnit.getViewValue()));
        holder.thirdDegreeTextView.setText(String.format(this.context.getString(R.string.main_default_temperature), (int)dto.getTemp().getEve(), this.degreeUnit.getViewValue()));
        holder.fourthDegreeTextView.setText(String.format(this.context.getString(R.string.main_default_temperature), (int)dto.getTemp().getNight(), this.degreeUnit.getViewValue()));
        if (dto.getWeather().length > 0) {
            holder.descriptionTextView.setText(StringExtension.capitalize(dto.getWeather()[0].getDescription()));
            Integer weatherIconId = this.context.getResources().getIdentifier("_" + dto.getWeather()[0].getIcon(), "drawable", this.context.getPackageName());
            holder.imageView.setImageResource(weatherIconId);
        }
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public void setTimezoneOffset(long timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    private String getLocalDate(long date, String pattern) {
        LocalDateTime ldt = LocalDateTime.ofEpochSecond(date + this.timezoneOffset, 0, ZoneOffset.UTC);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern, Locale.getDefault());
        return ldt.format(dtf);
    }
}
