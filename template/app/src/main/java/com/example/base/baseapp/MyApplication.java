package com.example.base.baseapp;

import android.app.Application;

import com.example.base.baseapp.dagger.AppComponent;
import com.example.base.baseapp.dagger.DaggerAppComponent;
import com.example.base.baseapp.dagger.module.AppModule;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class MyApplication extends Application {
    private AppComponent appComponent;
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = initAppComponent();

        // Used for enable the database inspector in chrome://inspect
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    private AppComponent initAppComponent() {
        return DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
