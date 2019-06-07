package com.example.base.baseapp.repository.local;

import com.example.base.baseapp.dagger.scope.AppScope;
import com.example.base.baseapp.model.ExampleData;
import com.example.base.baseapp.persistence.ExampleDataDatabase;
import com.example.base.baseapp.repository.ExampleDataRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

@AppScope
public class ExampleDataLocalRepository implements ExampleDataRepository {

    private ExampleDataDatabase exampleDataDatabase;

    @Inject
    public ExampleDataLocalRepository(ExampleDataDatabase exampleDataDatabase) {
        this.exampleDataDatabase = exampleDataDatabase;
    }

    @Override
    public Single<List<ExampleData>> getAllExampleData() {
        return exampleDataDatabase.exampleDataDao().getAllExampleData();
    }

    @Override
    public Completable insertExampleData(List<ExampleData> exampleDataList) {
        return Completable.fromAction(() -> exampleDataDatabase.exampleDataDao().insertExampleData(exampleDataList));
    }
}
