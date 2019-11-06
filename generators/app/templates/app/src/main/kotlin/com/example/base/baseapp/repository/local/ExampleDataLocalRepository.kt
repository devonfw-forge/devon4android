package com.example.base.baseapp.repository.local

import com.example.base.baseapp.model.ExampleData
import com.example.base.baseapp.persistence.ExampleDataDatabase
import com.example.base.baseapp.repository.ExampleDataRepository

import javax.inject.Inject

import io.reactivex.Completable
import io.reactivex.Single

class ExampleDataLocalRepository @Inject
constructor(private val exampleDataDatabase: ExampleDataDatabase) : ExampleDataRepository {

    override val allExampleData: Single<List<ExampleData>>
        get() = exampleDataDatabase.exampleDataDao().allExampleData

    override fun insertExampleData(exampleDataList: List<ExampleData>): Completable {
        return Completable.fromAction { exampleDataDatabase.exampleDataDao().insertExampleData(exampleDataList) }
    }
}
