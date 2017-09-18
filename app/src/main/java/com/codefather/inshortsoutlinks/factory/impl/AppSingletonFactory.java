package com.codefather.inshortsoutlinks.factory.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.codefather.inshortsoutlinks.factory.DbRepository;
import com.codefather.inshortsoutlinks.factory.NetworkManager;
import com.codefather.inshortsoutlinks.factory.SingletonFactory;

/**
 * Created by hitesh-lalwani on 18/9/17
 */

public class AppSingletonFactory implements SingletonFactory {

    private Context mAppContext;
    private NetworkManager networkManager;
    private DbRepository dbRepository;

    public AppSingletonFactory(@NonNull Context context) {
        mAppContext = context.getApplicationContext();
    }

    @Override
    public NetworkManager networkManager() {
        if (networkManager == null) {
            networkManager = new AppNetworkManager(mAppContext);
        }
        return networkManager;
    }

    @Override
    public DbRepository dbRepository() {
        if (dbRepository == null) {
            dbRepository = new AppDbRepository();
        }
        return dbRepository;
    }
}
