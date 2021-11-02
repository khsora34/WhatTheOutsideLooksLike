package com.setoncios.mobileappdevelopment.whattheoutsidelookslike.dtos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class DailyWeatherInfoDTO implements Serializable {
    private final long dt;
    private final TemperatureDetailDTO temp;
    private final WeatherRepresentationInfoDTO[] weather;
    private final double uvi;
    private final int pop;

    public DailyWeatherInfoDTO(JSONObject jsonObject) throws JSONException {
        this.dt = jsonObject.getLong("dt");
        this.temp = new TemperatureDetailDTO(jsonObject.getJSONObject("temp"));
        this.weather = WeatherRepresentationInfoDTO.parseFrom(jsonObject.getJSONArray("weather"));
        this.uvi = jsonObject.getDouble("uvi");
        this.pop = jsonObject.getInt("pop");
    }

    public static DailyWeatherInfoDTO[] parseFrom(JSONArray jsonArray) {
        ArrayList<DailyWeatherInfoDTO> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                list.add(new DailyWeatherInfoDTO(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list.toArray(new DailyWeatherInfoDTO[0]);
    }

    public long getDt() {
        return dt;
    }

    public TemperatureDetailDTO getTemp() {
        return temp;
    }

    public WeatherRepresentationInfoDTO[] getWeather() {
        return weather;
    }

    public double getUvi() {
        return uvi;
    }

    public int getPop() {
        return pop;
    }
}
