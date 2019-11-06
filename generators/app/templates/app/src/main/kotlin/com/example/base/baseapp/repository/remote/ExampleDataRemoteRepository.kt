package com.example.base.baseapp.repository.remote

import com.example.base.baseapp.model.ExampleData
import com.example.base.baseapp.repository.ExampleDataRepository

import io.reactivex.Completable
import io.reactivex.Single

//TODO: Override allExampleData and insertExampleData and then remove abstract modifier
abstract class ExampleDataRemoteRepository : ExampleDataRepository {
    //override val allExampleData: Single<List<ExampleData>>
    //get() = TODO: Create your network connection here

    //override fun insertExampleData(exampleDataList: List<ExampleData>): Completable {
    //TODO: Create your network connection here
    //}
}
