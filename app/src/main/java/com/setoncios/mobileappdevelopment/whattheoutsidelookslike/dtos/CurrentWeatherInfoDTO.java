package com.setoncios.mobileappdevelopment.whattheoutsidelookslike.dtos;

import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CurrentWeatherInfoDTO {
    private static final String TAG = "CurrentWeatherInfoDTO";
    private final long dt;
    private final long sunrise;
    private final long sunset;
    private final double temp;
    private final double feelsLike;
    private final int pressure;
    private final int humidity;
    private final double uvi;
    private final int clouds;
    private final double visibility;
    private final double windSpeed;
    private final int windDeg;
    @Nullable private Double windGust;
    private final WeatherRepresentationInfoDTO[] weather;
    @Nullable private PrecipitationsInfoDTO rain;
    @Nullable private PrecipitationsInfoDTO snow;

    public CurrentWeatherInfoDTO(JSONObject jsonObject) throws JSONException {
        this.dt = jsonObject.getLong("dt");
        this.sunrise = jsonObject.getLong("sunrise");
        this.sunset = jsonObject.getLong("sunset");
        this.temp = jsonObject.getDouble("temp");
        this.feelsLike = jsonObject.getDouble("feels_like");
        this.pressure = jsonObject.getInt("pressure");
        this.humidity = jsonObject.getInt("humidity");
        this.uvi = jsonObject.getDouble("uvi");
        this.clouds = jsonObject.getInt("clouds");
        this.visibility = jsonObject.getDouble("visibility");
        this.windSpeed = jsonObject.getDouble("wind_speed");
        this.windDeg = jsonObject.getInt("wind_deg");
        try {
            this.windGust = jsonObject.getDouble("wind_gust");
        } catch (JSONException e) {
            Log.d(TAG, "CurrentWeatherInfoDTO: Field windGust not available.");
        }
        this.weather = WeatherRepresentationInfoDTO.parseFrom(jsonObject.getJSONArray("weather"));
        try {
            this.rain = new PrecipitationsInfoDTO(jsonObject.getJSONObject("rain"));
        } catch (JSONException e) {
            Log.d(TAG, "CurrentWeatherInfoDTO: Field rain not available.");
        }
        try {
            this.snow = new PrecipitationsInfoDTO(jsonObject.getJSONObject("snow"));
        } catch (JSONException e) {
            Log.d(TAG, "CurrentWeatherInfoDTO: Field snow not available.");
        }
    }

    public long getDt() {
        return dt;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public double getTemp() {
        return temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getUvi() {
        return uvi;
    }

    public int getClouds() {
        return clouds;
    }

    public double getVisibility() {
        return visibility;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getWindDeg() {
        return windDeg;
    }

    @Nullable
    public Double getWindGust() {
        return windGust;
    }

    public WeatherRepresentationInfoDTO[] getWeather() {
        return weather;
    }

    @Nullable
    public PrecipitationsInfoDTO getRain() {
        return rain;
    }

    @Nullable
    public PrecipitationsInfoDTO getSnow() {
        return snow;
    }
}
