package com.example.base.baseapp.repository

import com.example.base.baseapp.model.ExampleData

import io.reactivex.Completable
import io.reactivex.Single

interface ExampleDataRepository {
    val allExampleData: Single<List<ExampleData>>

    fun insertExampleData(exampleDataList: List<ExampleData>): Completable
}
