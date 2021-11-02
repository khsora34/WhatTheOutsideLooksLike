package com.setoncios.mobileappdevelopment.whattheoutsidelookslike.dtos;

import org.json.JSONException;
import org.json.JSONObject;

public class PrecipitationsInfoDTO {
    private double probability;

    public PrecipitationsInfoDTO(JSONObject jsonObject) throws JSONException {
        this.probability = jsonObject.getDouble("1h");
    }
}
