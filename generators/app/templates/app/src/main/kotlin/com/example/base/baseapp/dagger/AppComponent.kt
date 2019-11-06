package com.example.base.baseapp.dagger

import com.capgemini.templateapp.dagger.scope.AppScope
import com.example.base.baseapp.MyApplication
import com.example.base.baseapp.dagger.module.AppModule
import com.example.base.baseapp.dagger.module.LocalStorageModule
import com.example.base.baseapp.dagger.module.SchedulersModule

import dagger.Component

@Component(modules = [(AppModule::class), (SchedulersModule::class), (LocalStorageModule::class)])
@AppScope
interface AppComponent : BaseAppComponent {
    fun nonConfiguration(): ActivityComponent
    fun inject(myApplication: MyApplication)
}