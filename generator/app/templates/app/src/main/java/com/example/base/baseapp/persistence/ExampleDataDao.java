package com.example.base.baseapp.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.base.baseapp.model.ExampleData;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ExampleDataDao {

    @Query("SELECT * FROM exampledata")
    Single<List<ExampleData>> getAllExampleData();

    @Query("SELECT * FROM exampledata WHERE content LIKE :id LIMIT 1")
    Single<ExampleData> findExampleDataById(String id);

    @Insert
    void insertExampleData(List<ExampleData> exampleDataList);

    @Delete
    void deleteExampleData(ExampleData exampleData);
}
