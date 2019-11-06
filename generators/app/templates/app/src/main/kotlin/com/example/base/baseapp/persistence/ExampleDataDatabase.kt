package com.example.base.baseapp.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.base.baseapp.model.ExampleData;

@Database(entities = [ExampleData::class], version = 1, exportSchema = false)
abstract class ExampleDataDatabase : RoomDatabase() {
    abstract fun exampleDataDao(): ExampleDataDao
}