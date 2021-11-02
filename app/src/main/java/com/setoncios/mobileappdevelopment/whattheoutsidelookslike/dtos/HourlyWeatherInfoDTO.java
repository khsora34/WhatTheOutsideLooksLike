package com.setoncios.mobileappdevelopment.whattheoutsidelookslike.dtos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class HourlyWeatherInfoDTO implements Serializable {
    private final long dt;
    private final double temp;
    private final WeatherRepresentationInfoDTO[] weather;
    private final int pop;

    public HourlyWeatherInfoDTO(JSONObject jsonObject) throws JSONException {
        this.dt = jsonObject.getLong("dt");
        this.temp = jsonObject.getDouble("temp");
        this.weather = WeatherRepresentationInfoDTO.parseFrom(jsonObject.getJSONArray("weather"));
        this.pop = jsonObject.getInt("pop");
    }

    public static HourlyWeatherInfoDTO[] parseFrom(JSONArray jsonArray) {
        ArrayList<HourlyWeatherInfoDTO> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                list.add(new HourlyWeatherInfoDTO(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list.toArray(new HourlyWeatherInfoDTO[0]);
    }

    public long getDt() {
        return dt;
    }

    public double getTemp() {
        return temp;
    }

    public WeatherRepresentationInfoDTO[] getWeather() {
        return weather;
    }

    public int getPop() {
        return pop;
    }
}
