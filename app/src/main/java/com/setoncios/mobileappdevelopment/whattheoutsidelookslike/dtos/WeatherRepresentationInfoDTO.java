package com.setoncios.mobileappdevelopment.whattheoutsidelookslike.dtos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class WeatherRepresentationInfoDTO implements Serializable {
    private final Integer id;
    private final String main;
    private final String description;
    private final String icon;

    public WeatherRepresentationInfoDTO(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt("id");
        this.main = jsonObject.getString("main");
        this.description = jsonObject.getString("description");
        this.icon = jsonObject.getString("icon");
    }

    public static WeatherRepresentationInfoDTO[] parseFrom(JSONArray jsonArray) {
        ArrayList<WeatherRepresentationInfoDTO> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                list.add(new WeatherRepresentationInfoDTO(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list.toArray(new WeatherRepresentationInfoDTO[0]);
    }

    public Integer getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
