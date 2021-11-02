package com.setoncios.mobileappdevelopment.whattheoutsidelookslike.dtos;

import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.R;

public enum DegreeUnit {
    celsius, fahrenheit;

    public DegreeUnit toggle() {
        switch (this) {
            case celsius:
                return fahrenheit;
            case fahrenheit:
                return celsius;
        }
        return null;
    }

    public Integer getIconId() {
        switch (this) {
            case celsius:
                return R.drawable.units_c;
            case fahrenheit:
                return R.drawable.units_f;
        }
        return null;
    }

    public String getMenuTitleValue() {
        switch (this) {
            case celsius:
                return "Metric";
            case fahrenheit:
                return "Imperial";
        }
        return null;
    }

    public String getViewValue() {
        switch (this) {
            case celsius:
                return "C";
            case fahrenheit:
                return "F";
        }
        return null;
    }

    public String getApiValue() {
        switch (this) {
            case celsius:
                return "metric";
            case fahrenheit:
                return "imperial";
        }
        return null;
    }

    public String getSpeedMeasure() {
        switch (this) {
            case celsius:
                return "mps";
            case fahrenheit:
                return "miph";
        }
        return null;
    }

    public String getDistanceMeasure() {
        switch (this) {
            case celsius:
                return "m";
            case fahrenheit:
                return "mi";
        }
        return null;
    }
}
