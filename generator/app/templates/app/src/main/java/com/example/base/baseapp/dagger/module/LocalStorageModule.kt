package com.example.base.baseapp.dagger.module

import android.arch.persistence.room.Room
import android.content.Context

import com.example.base.baseapp.persistence.ExampleDataDatabase
import com.example.base.baseapp.repository.ExampleDataRepository
import com.example.base.baseapp.repository.local.ExampleDataLocalRepository

import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [(LocalStorageModule.Declarations::class)])
class LocalStorageModule {

    @Module
    interface Declarations {
        @Binds
        fun provideExampleDataRepository(exampleDataLocalRepository: ExampleDataLocalRepository): ExampleDataRepository
    }

    @Provides
    internal fun provideExampleDataRepository(exampleDataDatabase: ExampleDataDatabase): ExampleDataLocalRepository {
        return ExampleDataLocalRepository(exampleDataDatabase)
    }

    @Provides
    internal fun providesExampleDataDatabase(context: Context): ExampleDataDatabase {
        return Room.inMemoryDatabaseBuilder(context,
                ExampleDataDatabase::class.java)
                .fallbackToDestructiveMigration()
                .build()
    }
}
