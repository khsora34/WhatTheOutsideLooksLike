package com.setoncios.mobileappdevelopment.whattheoutsidelookslike;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

public class LocationProvider {
    private Activity activity;

    public LocationProvider(Activity activity) {
        this.activity = activity;
    }

    public String getLocationName(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(activity);
        try {
            List<Address> address = geocoder.getFromLocation(latitude, longitude, 1);
            if (address == null || address.isEmpty()) { // Nothing returned!
                return null;
            }
            String country = address.get(0).getCountryCode();
            String p1 = "";
            String p2 = "";
            if (country.equals("US")) {
                p1 = address.get(0).getLocality();
                p2 = address.get(0).getAdminArea();
            } else {
                p1 = address.get(0).getLocality();
                if (p1 == null)
                    p1 = address.get(0).getSubAdminArea();
                p2 = address.get(0).getCountryName();
            }
            return p1 + ", " + p2;
        } catch (IOException e) {
            // Failure to get an Address object
            return null;
        }
    }

    public double[] getLatLon(String userProvidedLocation) {
        Geocoder geocoder = new Geocoder(activity);
        try {
            List<Address> address = geocoder.getFromLocationName(userProvidedLocation, 1);
            if (address == null || address.isEmpty()) { // Nothing returned!
                return null;
            }
            double lat = address.get(0).getLatitude();
            double lon = address.get(0).getLongitude();
            return new double[]{lat, lon};
        } catch (IOException e) {
// Failure to get an Address object return null;
            return null;
        }
    }
}
