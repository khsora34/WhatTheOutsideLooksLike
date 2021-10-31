package com.setoncios.mobileappdevelopment.whattheoutsidelookslike;

public enum DegreeUnit {
    celsius, fahrenheit;

    String getApiValue() {
        switch (this) {
            case celsius:
                return "metric";
            case fahrenheit:
                return "imperial";
        }
        return null;
    }
}
