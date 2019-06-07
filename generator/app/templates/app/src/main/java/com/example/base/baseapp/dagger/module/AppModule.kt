package com.example.base.baseapp.dagger.module

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {

    @Provides
    fun context(): Context {
        return context
    }
}