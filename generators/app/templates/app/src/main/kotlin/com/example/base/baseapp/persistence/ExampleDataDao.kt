package com.example.base.baseapp.persistence;

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

import com.example.base.baseapp.model.ExampleData;

import io.reactivex.Single

@Dao
interface ExampleDataDao {

    @get:Query("SELECT * FROM exampledata")
    val allExampleData: Single<List<ExampleData>>

    @Query("SELECT * FROM exampledata WHERE content LIKE :id LIMIT 1")
    fun findExampleDataById(id: String): Single<ExampleData>

    @Insert
    fun insertExampleData(exampleDataList: List<ExampleData>)

    @Delete
    fun deleteExampleData(exampleData: ExampleData)
}
