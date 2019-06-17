package com.example.base.baseapp

import android.app.Application

import com.example.base.baseapp.dagger.BaseAppComponent
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher


abstract class BaseApplication<T : BaseAppComponent> : Application() {

    var appComponent: T? = null
        private set
    var refWatcher: RefWatcher? = null
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = initAppComponent()

        // Used for enable the database inspector in chrome://inspect
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        refWatcher = LeakCanary.install(this)
    }

    protected abstract fun initAppComponent(): T
}
