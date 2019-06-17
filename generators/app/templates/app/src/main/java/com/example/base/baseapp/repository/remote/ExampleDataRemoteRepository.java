package com.example.base.baseapp.repository.remote;

import com.example.base.baseapp.model.ExampleData;
import com.example.base.baseapp.repository.ExampleDataRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ExampleDataRemoteRepository implements ExampleDataRepository {
    @Override
    public Single<List<ExampleData>> getAllExampleData() {
        //TODO: Create your network connection here
        return null;
    }

    @Override
    public Completable insertExampleData(List<ExampleData> exampleDataList) {
        return null;
    }
}
