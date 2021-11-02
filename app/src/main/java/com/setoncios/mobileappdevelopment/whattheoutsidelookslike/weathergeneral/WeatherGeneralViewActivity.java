package com.setoncios.mobileappdevelopment.whattheoutsidelookslike.weathergeneral;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.ConnectionHelper;
import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.DegreeUnit;
import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.LocationProvider;
import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.R;
import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.StringExtension;
import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.dtos.HourlyWeatherInfoDTO;
import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.dtos.MainWeatherContentDTO;
import com.setoncios.mobileappdevelopment.whattheoutsidelookslike.weekforecast.WeekForecastActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class WeatherGeneralViewActivity extends AppCompatActivity {
    private TextView cityTextView;
    private TextView dateTextView;
    private TextView degreeTextView;
    private TextView feelLikeTextView;
    private ImageView imageView;
    private TextView descriptionTextView;
    private TextView windTextView;
    private TextView humidityTextView;
    private TextView uviTextView;
    private TextView visibilityTextView;
    private TextView firstDegreeTextView;
    private TextView secondDegreeTextView;
    private TextView thirdDegreeTextView;
    private TextView fourthDegreeTextView;
    private RecyclerView recyclerView;
    private TextView sunriseTextView;
    private TextView sunsetTextView;
    private Menu menu;

    private HourlyWeatherAdapter adapter;
    private LocationProvider locationProvider;
    private ConnectionHelper connectionHelper;

    private MainWeatherContentDTO dto;
    private final ArrayList<HourlyWeatherInfoDTO> list = new ArrayList<>();
    private DegreeUnit degreeUnit = DegreeUnit.celsius;
    private double latitude = 41.8675766;
    private double longitude = -87.616232;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.loadIds();
        this.connectionHelper = new ConnectionHelper(this);
        this.locationProvider = new LocationProvider(this);
        this.adapter = new HourlyWeatherAdapter(this.list, this.degreeUnit, this);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        this.cityTextView.setText(locationProvider.getLocationName(this.latitude, this.longitude));
        this.loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.general_weather_actions_menu, menu);
        this.menu = menu;
        this.updateDegreeMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (!connectionHelper.hasNetworkConnection()) {
            this.enableNoInternetMode();
            return false;
        }
        if (item.getItemId() == R.id.menu_item_degree) {
            this.degreeUnit = this.degreeUnit.toggle();
            this.updateDegreeMenu();
            this.adapter.setDegreeUnit(this.degreeUnit);
            this.loadData();
            return true;
        } else if (item.getItemId() == R.id.menu_item_calendar) {
            this.launchWeekForecast();
            return true;
        } else if (item.getItemId() == R.id.menu_item_location) {
            this.showLocationDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void receiveData(String response) {
        if (response == null) {
            this.enableNoInternetMode();
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            this.dto = new MainWeatherContentDTO(jsonObject);
            this.fillView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        Runnable runnable = new WeatherRunnable(this.latitude, this.longitude, this.degreeUnit, this);
        new Thread(runnable).start();
    }

    private void loadIds() {
        this.cityTextView = findViewById(R.id.mainActivityCityTextView);
        this.dateTextView = findViewById(R.id.mainActivityTimeTextView);
        this.degreeTextView = findViewById(R.id.mainActivityTemperatureTextView);
        this.feelLikeTextView = findViewById(R.id.mainActivityFeelingTextView);
        this.imageView = findViewById(R.id.mainActivityWeatherImageView);
        this.descriptionTextView = findViewById(R.id.mainActivityDescriptionTextView);
        this.windTextView = findViewById(R.id.mainActivityWindTextView);
        this.humidityTextView = findViewById(R.id.mainActivityHumidityTextView);
        this.uviTextView = findViewById(R.id.mainActivityUVTextView);
        this.visibilityTextView = findViewById(R.id.mainActivityVisibilityTextView);
        this.firstDegreeTextView = findViewById(R.id.mainActivityFirstTemperatureTextView);
        this.secondDegreeTextView = findViewById(R.id.mainActivitySecondTemperatureTextView);
        this.thirdDegreeTextView = findViewById(R.id.mainActivityThirdTemperatureTextView);
        this.fourthDegreeTextView = findViewById(R.id.mainActivityFourthTemperatureTextView);
        this.recyclerView = findViewById(R.id.hourlyWeatherRecyclerView);
        this.sunriseTextView = findViewById(R.id.mainActivitySunriseTextView);
        this.sunsetTextView = findViewById(R.id.mainActivitySunsetTextView);
    }

    private void showLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setGravity(Gravity.CENTER_HORIZONTAL);
        builder.setView(editText);
        builder.setMessage(R.string.location_dialog_message);
        builder.setTitle(R.string.location_dialog_title);
        builder.setPositiveButton(R.string.generic_ok, (dialog, id) -> didTapOkOnLocationDialog(editText));
        builder.setNegativeButton(R.string.generic_cancel, (dialogInterface, i) -> {});
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void didTapOkOnLocationDialog(EditText editText) {
        double[] coordinates = locationProvider.getLatLon(editText.getText().toString());
        if (coordinates == null) {
            this.showToast(getString(R.string.error_message_location));
        } else {
            this.latitude = coordinates[0];
            this.longitude = coordinates[1];
            this.cityTextView.setText(editText.getText());
            this.loadData();
        }
    }

    private void showToast(String message) {
        Toast toast = new Toast(this);
        toast.setText(message);
        toast.show();
    }

    private void updateDegreeMenu() {
        if (this.degreeUnit.getIconId() != null) {
            this.menu.getItem(0).setIcon(this.degreeUnit.getIconId());
        }
        this.menu.getItem(0).setTitle(this.degreeUnit.getMenuTitleValue());
    }

    private void launchWeekForecast() {
        Intent intent = new Intent(WeatherGeneralViewActivity.this, WeekForecastActivity.class);
        intent.putExtra(getString(R.string.intent_list), this.dto.getDaily());
        intent.putExtra(getString(R.string.intent_degree_unit), this.degreeUnit);
        intent.putExtra(getString(R.string.intent_location_name), this.cityTextView.getText().toString());
        intent.putExtra(getString(R.string.intent_timezone_offset), this.dto.getTimezoneOffset());
        this.startActivity(intent);
    }

    private void fillView() {
        if (dto == null) {
            return;
        }
        this.adapter.setTimezoneOffset(this.dto.getTimezoneOffset());
        String proposedNameForLocation = locationProvider.getLocationName(this.latitude, this.longitude);
        if (proposedNameForLocation != null) {
            this.cityTextView.setText(proposedNameForLocation);
        }
        this.dateTextView.setText(getLocalDate(dto.getCurrent().getDt(), "EEE MMM dd h:mm a, yyyy"));
        this.degreeTextView.setText(String.format(getString(R.string.main_default_temperature), (int)this.dto.getCurrent().getTemp(), this.degreeUnit.getViewValue()));
        this.feelLikeTextView.setText(String.format(getString(R.string.main_feel_like), (int)this.dto.getCurrent().getFeelsLike(), this.degreeUnit.getViewValue()));
        this.humidityTextView.setText(String.format(getString(R.string.main_humidity), this.dto.getCurrent().getHumidity()));
        this.uviTextView.setText(String.format(getString(R.string.main_uv_index), (int)this.dto.getCurrent().getUvi()));
        int weatherIconId = this.getResources().getIdentifier("_" + this.dto.getCurrent().getWeather()[0].getIcon(), "drawable", getPackageName());
        this.imageView.setImageResource(weatherIconId);
        if (this.dto.getCurrent().getWeather().length > 0) {
            this.descriptionTextView.setText(String.format(getString(R.string.main_description_clouds), StringExtension.capitalize(this.dto.getCurrent().getWeather()[0].getDescription()), this.dto.getCurrent().getClouds()));
        }
        this.setWindTextView();
        this.setVisibilityTextView();
        if (this.dto.getDaily().length > 0) {
            this.firstDegreeTextView.setText(String.format(getString(R.string.main_default_temperature), (int)this.dto.getDaily()[0].getTemp().getMorn(), this.degreeUnit.getViewValue()));
            this.secondDegreeTextView.setText(String.format(getString(R.string.main_default_temperature), (int)this.dto.getDaily()[0].getTemp().getDay(), this.degreeUnit.getViewValue()));
            this.thirdDegreeTextView.setText(String.format(getString(R.string.main_default_temperature), (int)this.dto.getDaily()[0].getTemp().getEve(), this.degreeUnit.getViewValue()));
            this.fourthDegreeTextView.setText(String.format(getString(R.string.main_default_temperature), (int)this.dto.getDaily()[0].getTemp().getNight(), this.degreeUnit.getViewValue()));
        }
        this.list.clear();
        this.list.addAll(Arrays.asList(this.dto.getHourly()));
        this.adapter.notifyItemRangeChanged(0, this.dto.getHourly().length);
        this.sunriseTextView.setText(String.format(getString(R.string.main_sunrise), this.getLocalDate(this.dto.getCurrent().getSunrise(), "h:mm a")));
        this.sunsetTextView.setText(String.format(getString(R.string.main_sunset), this.getLocalDate(this.dto.getCurrent().getSunset(), "h:mm a")));
    }

    private void setWindTextView() {
        if (this.dto.getCurrent().getWindGust() != null) {
            this.windTextView.setText(String.format(getString(R.string.main_wind_gust), this.getDirection(this.dto.getCurrent().getWindDeg()), (int)this.dto.getCurrent().getWindSpeed(), this.degreeUnit.getSpeedMeasure(), this.dto.getCurrent().getWindGust().intValue(), this.degreeUnit.getSpeedMeasure()));
        } else {
            this.windTextView.setText(String.format(getString(R.string.main_wind), this.getDirection(this.dto.getCurrent().getWindDeg()), (int)this.dto.getCurrent().getWindSpeed(), this.degreeUnit.getSpeedMeasure()));
        }
    }

    private void setVisibilityTextView() {
        double visibility = this.dto.getCurrent().getVisibility();
        if (this.degreeUnit == DegreeUnit.fahrenheit) {
            visibility /= 1609.344;
        }
        DecimalFormat myFormatter = new DecimalFormat("#.##");
        this.visibilityTextView.setText(String.format(getString(R.string.main_visibility), myFormatter.format(visibility), this.degreeUnit.getDistanceMeasure()));
    }

    private String getLocalDate(long date, String pattern) {
        LocalDateTime ldt = LocalDateTime.ofEpochSecond(date + this.dto.getTimezoneOffset(), 0, ZoneOffset.UTC);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern, Locale.getDefault());
        return ldt.format(dtf);
    }

    private void enableNoInternetMode() {
        this.dto = null;
        this.adapter.setTimezoneOffset(0);
        this.cityTextView.setText("");
        this.dateTextView.setText(R.string.generic_error_internet);
        this.degreeTextView.setText("");
        this.feelLikeTextView.setText("");
        this.humidityTextView.setText("");
        this.uviTextView.setText("");
        this.imageView.setImageAlpha(0);
        this.descriptionTextView.setText("");
        this.windTextView.setText("");
        this.visibilityTextView.setText("");
        this.firstDegreeTextView.setText("");
        this.secondDegreeTextView.setText("");
        this.thirdDegreeTextView.setText("");
        this.fourthDegreeTextView.setText("");
        int oldListSize = this.list.size();
        this.list.clear();
        this.adapter.notifyItemRangeRemoved(0, oldListSize);
        this.sunriseTextView.setText("");
        this.sunsetTextView.setText("");
    }

    private String getDirection(double degrees) {
        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "X"; // We'll use 'X' as the default if we get a bad value
    }
}