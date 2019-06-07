package com.example.base.baseapp.dagger.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.base.baseapp.dagger.scope.AppScope;
import com.example.base.baseapp.persistence.ExampleDataDatabase;
import com.example.base.baseapp.repository.ExampleDataRepository;
import com.example.base.baseapp.repository.local.ExampleDataLocalRepository;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = LocalStorageModule.Declarations.class)
public class LocalStorageModule {

    @Module
    public interface Declarations {
        @Binds
        ExampleDataRepository provideExampleDataRepository(ExampleDataLocalRepository exampleDataLocalRepository);
    }

    @Provides
    @AppScope
    ExampleDataLocalRepository provideExampleDataRepository(ExampleDataDatabase exampleDataDatabase) {
        return new ExampleDataLocalRepository(exampleDataDatabase);
    }

    @Provides
    @AppScope
    ExampleDataDatabase providesExampleDataDatabase(Context context) {
        return Room.inMemoryDatabaseBuilder(context,
                ExampleDataDatabase.class)
                .fallbackToDestructiveMigration()
                .build();
    }
}
