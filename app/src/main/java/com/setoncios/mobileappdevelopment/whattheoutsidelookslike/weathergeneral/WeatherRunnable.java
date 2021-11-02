package com.setoncios.mobileappdevelopment.whattheoutsidelookslike.weathergeneral;

import android.net.Uri;

import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.ConnectionHelper;
import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.dtos.DegreeUnit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherRunnable implements Runnable {
    private static final String url = "https://api.openweathermap.org/data/2.5/onecall";
    private static final String key = "9b9688ec986b42845e1f282ebce725ee";
    private static final String latitudeParam = "lat";
    private static final String longitudeParam = "lon";
    private static final String appIdParam = "appid";
    private static final String unitsParam = "units";
    private static final String langParam = "lang";
    private static final String excludeParam = "exclude";
    private ConnectionHelper connectionHelper;

    private final double latitude;
    private final double longitude;
    private final DegreeUnit units;
    private final WeatherGeneralViewActivity activity;

    WeatherRunnable(double latitude, double longitude, DegreeUnit units, WeatherGeneralViewActivity activity) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.units = units;
        this.activity = activity;
        this.connectionHelper = new ConnectionHelper(activity);
    }

    @Override
    public void run() {
        Uri uri = Uri.parse(url)
                .buildUpon()
                .appendQueryParameter(latitudeParam, String.valueOf(latitude))
                .appendQueryParameter(longitudeParam, String.valueOf(longitude))
                .appendQueryParameter(appIdParam, key)
                .appendQueryParameter(unitsParam, units.getApiValue())
                .appendQueryParameter(langParam, "en")
                .appendQueryParameter(excludeParam, "minutely")
                .build();
        String urlString = uri.toString();
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (!connectionHelper.hasNetworkConnection()) {
                this.activity.receiveData(null);
                return;
            }
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                // TODO: Add error handle.
                return;
            }
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
            this.activity.runOnUiThread(() -> {
                this.activity.receiveData(builder.toString());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
