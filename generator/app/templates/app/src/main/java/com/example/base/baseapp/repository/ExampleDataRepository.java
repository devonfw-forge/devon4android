package com.example.base.baseapp.repository;

import com.example.base.baseapp.model.ExampleData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface ExampleDataRepository {
    Single<List<ExampleData>> getAllExampleData();

    Completable insertExampleData(List<ExampleData> exampleDataList);
}
