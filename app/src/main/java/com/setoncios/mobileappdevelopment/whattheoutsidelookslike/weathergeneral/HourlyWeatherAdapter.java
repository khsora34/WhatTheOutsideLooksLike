package com.setoncios.mobileappdevelopment.whattheoutsidelookslike.weathergeneral;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.DegreeUnit;
import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.R;
import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.StringExtension;
import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.dtos.HourlyWeatherInfoDTO;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherViewHolder> {
    private List<HourlyWeatherInfoDTO> list;
    private long timezoneOffset = 0;
    private DegreeUnit degreeUnit;
    private Context context;

    public HourlyWeatherAdapter(List<HourlyWeatherInfoDTO> list, DegreeUnit degreeUnit, Context context) {
        this.list = list;
        this.degreeUnit = degreeUnit;
        this.context = context;
    }

    @NonNull
    @Override
    public HourlyWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_cell, parent, false);
        return new HourlyWeatherViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyWeatherViewHolder holder, int position) {
        HourlyWeatherInfoDTO dto = list.get(position);
        if (getLocalDate(dto.getDt()).getDayOfYear() == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
            holder.dayTextView.setText(R.string.main_cell_today);
        } else {
            holder.dayTextView.setText(getLocalDate(dto.getDt(), "eeee"));
        }
        holder.timeTextView.setText(getLocalDate(dto.getDt(), "hh:mm a"));
        holder.temperatureTextView.setText(String.format(this.context.getString(R.string.main_default_temperature), (int)dto.getTemp(), this.degreeUnit.getViewValue()));
        if (dto.getWeather().length > 0) {
            holder.imageDescriptionTextView.setText(StringExtension.capitalize(dto.getWeather()[0].getDescription()));
            Integer weatherIconId = this.context.getResources().getIdentifier("_" + dto.getWeather()[0].getIcon(), "drawable", this.context.getPackageName());
            holder.imageView.setImageResource(weatherIconId);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setTimezoneOffset(long timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public void setDegreeUnit(DegreeUnit degreeUnit) {
        this.degreeUnit = degreeUnit;
    }

    private LocalDateTime getLocalDate(long date) {
        LocalDateTime ldt = LocalDateTime.ofEpochSecond(date + this.timezoneOffset, 0, ZoneOffset.UTC);
        return ldt;
    }

    private String getLocalDate(long date, String pattern) {
        LocalDateTime ldt = LocalDateTime.ofEpochSecond(date + this.timezoneOffset, 0, ZoneOffset.UTC);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern, Locale.getDefault());
        return ldt.format(dtf);
    }
}
