package com.setoncios.mobileappdevelopment.whattheoutsidelookslike.dtos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class TemperatureDetailDTO implements Serializable {
    private final double day;
    private final double min;
    private final double max;
    private final double night;
    private final double eve;
    private final double morn;

    public TemperatureDetailDTO(JSONObject jsonObject) throws JSONException {
        this.day = jsonObject.getDouble("day");
        this.min = jsonObject.getDouble("min");
        this.max = jsonObject.getDouble("max");
        this.night = jsonObject.getDouble("night");
        this.eve = jsonObject.getDouble("eve");
        this.morn = jsonObject.getDouble("morn");
    }

    public double getDay() {
        return day;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getNight() {
        return night;
    }

    public double getEve() {
        return eve;
    }

    public double getMorn() {
        return morn;
    }
}
