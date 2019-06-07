package com.example.base.baseapp.dagger;

import com.example.base.baseapp.MyApplication;
import com.example.base.baseapp.dagger.module.AppModule;
import com.example.base.baseapp.dagger.module.LocalStorageModule;
import com.example.base.baseapp.dagger.module.SchedulersModule;
import com.example.base.baseapp.dagger.scope.AppScope;

import dagger.Component;

@Component(modules = {AppModule.class, SchedulersModule.class, LocalStorageModule.class})
@AppScope
public interface AppComponent {
    ActivityComponent nonConfiguration();

    void inject(MyApplication myApplication);
}