package com.example.base.baseapp;


import com.example.base.baseapp.dagger.AppComponent;
import com.example.base.baseapp.dagger.module.AppModule;
import com.example.base.baseapp.dagger.DaggerAppComponent;

class MyApplication : BaseApplication<AppComponent>() {

    override fun initAppComponent(): AppComponent {
        return DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }
}
