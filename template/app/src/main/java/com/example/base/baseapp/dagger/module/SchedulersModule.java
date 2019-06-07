package com.example.base.baseapp.dagger.module;

import com.example.base.baseapp.dagger.annotation.ComputationScheduler;
import com.example.base.baseapp.dagger.annotation.IOScheduler;
import com.example.base.baseapp.dagger.annotation.UiScheduler;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class SchedulersModule {

    @Provides
    @UiScheduler
    public Scheduler providesUiScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @IOScheduler
    public Scheduler providesIOScheduler() {
        return Schedulers.io();
    }

    @Provides
    @ComputationScheduler
    public Scheduler providesComputationScheduler() {
        return Schedulers.computation();
    }
}
