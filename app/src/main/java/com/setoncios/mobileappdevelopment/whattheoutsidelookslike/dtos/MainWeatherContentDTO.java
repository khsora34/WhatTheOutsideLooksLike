package com.setoncios.mobileappdevelopment.whattheoutsidelookslike.dtos;

import org.json.JSONException;
import org.json.JSONObject;

public class MainWeatherContentDTO {
    private final double latitude;
    private final double longitude;
    private final String timezone;
    private final int timezoneOffset;
    private final CurrentWeatherInfoDTO current;
    private final HourlyWeatherInfoDTO[] hourly;
    private final DailyWeatherInfoDTO[] daily;

    public MainWeatherContentDTO(JSONObject jsonObject) throws JSONException {
        this.latitude = jsonObject.getDouble("lat");
        this.longitude = jsonObject.getDouble("lon");
        this.timezone = jsonObject.getString("timezone");
        this.timezoneOffset = jsonObject.getInt("timezone_offset");
        this.current = new CurrentWeatherInfoDTO(jsonObject.getJSONObject("current"));
        this.hourly = HourlyWeatherInfoDTO.parseFrom(jsonObject.getJSONArray("hourly"));
        this.daily = DailyWeatherInfoDTO.parseFrom(jsonObject.getJSONArray("daily"));
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public int getTimezoneOffset() {
        return timezoneOffset;
    }

    public CurrentWeatherInfoDTO getCurrent() {
        return current;
    }

    public HourlyWeatherInfoDTO[] getHourly() {
        return hourly;
    }

    public DailyWeatherInfoDTO[] getDaily() {
        return daily;
    }
}
