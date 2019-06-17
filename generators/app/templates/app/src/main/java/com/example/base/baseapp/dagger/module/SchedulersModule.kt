package com.example.base.baseapp.dagger.module

import com.example.base.baseapp.dagger.annotation.ComputationScheduler
import com.example.base.baseapp.dagger.annotation.IOScheduler
import com.example.base.baseapp.dagger.annotation.UiScheduler

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class SchedulersModule {

    @Provides
    @UiScheduler
    fun providesUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    @IOScheduler
    fun providesIOScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @ComputationScheduler
    fun providesComputationScheduler(): Scheduler {
        return Schedulers.computation()
    }
}
