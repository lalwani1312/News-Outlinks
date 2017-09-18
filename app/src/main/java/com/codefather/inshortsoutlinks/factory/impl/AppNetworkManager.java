package com.codefather.inshortsoutlinks.factory.impl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.codefather.inshortsoutlinks.factory.NetworkManager;

/**
 * Created by hitesh-lalwani on 18/9/17.
 */

public class AppNetworkManager implements NetworkManager {

    private Context mAppContext;

    public AppNetworkManager(@NonNull Context context) {
        mAppContext = context.getApplicationContext();
    }

    @Override
    public boolean isInternetAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mAppContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
