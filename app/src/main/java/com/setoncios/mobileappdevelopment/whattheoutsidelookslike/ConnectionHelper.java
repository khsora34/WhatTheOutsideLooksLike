package com.setoncios.mobileappdevelopment.whattheoutsidelookslike;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionHelper {
    private Context context;

    public ConnectionHelper(Context context) {
        this.context = context;
    }
    public boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = context.getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }
}
