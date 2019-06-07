package com.example.base.baseapp;


import com.example.base.baseapp.dagger.AppComponent;
import com.example.base.baseapp.dagger.module.AppModule;

public class MyApplication extends BaseApplication<AppComponent> {

    @Override
    protected AppComponent initAppComponent() {
        return DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }
}
