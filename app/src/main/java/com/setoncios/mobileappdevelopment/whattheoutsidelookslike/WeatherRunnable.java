package com.setoncios.mobileappdevelopment.whattheoutsidelookslike;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherRunnable implements Runnable {
    private static final String url = "https://api.openweathermap.org/data/2.5/onecall";
    private static final String key = "9b9688ec986b42845e1f282ebce725ee";
    private static final String latitudeParam = "lat";
    private static final String longitudeParam = "lon";
    private static final String appidParam = "appid";
    private static final String unitsParam = "units";
    private static final String langParam = "lang";
    private static final String excludeParam = "exclude";

    private double latitude;
    private double longitude;
    private DegreeUnit units;
    private WeatherGeneralViewActivity mainActivity;

    WeatherRunnable(double latitude, double longitude, DegreeUnit units, WeatherGeneralViewActivity mainActivity) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.units = units;
        this.mainActivity = mainActivity;
    }

    @Override
    public void run() {
        Uri uri = Uri.parse(url)
                .buildUpon()
                .appendQueryParameter(latitudeParam, String.valueOf(latitude))
                .appendQueryParameter(longitudeParam, String.valueOf(longitude))
                .appendQueryParameter(appidParam, key)
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
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return;
            }

            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line;
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(builder.toString());
    }
}
