package com.codefather.inshortsoutlinks;

import android.app.Application;

import com.codefather.inshortsoutlinks.factory.SingletonFactory;
import com.codefather.inshortsoutlinks.factory.impl.AppSingletonFactory;
import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by hitesh-lalwani on 18/9/17.
 */

public class Outlinks extends Application {

    private static Outlinks sInstance;

    private SingletonFactory factory;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        FlowManager.init(this);
        factory = new AppSingletonFactory(this);
        Stetho.initializeWithDefaults(this);
    }

    public static Outlinks getInstance() {
        return sInstance;
    }

    public SingletonFactory factory() {
        return factory;
    }
}
