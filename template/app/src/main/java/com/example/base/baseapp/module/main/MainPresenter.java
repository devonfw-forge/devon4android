package com.example.base.baseapp.module.main;

import com.example.base.baseapp.dagger.annotation.ComputationScheduler;
import com.example.base.baseapp.dagger.annotation.IOScheduler;
import com.example.base.baseapp.dagger.annotation.UiScheduler;
import com.example.base.baseapp.dagger.scope.ActivityScope;
import com.example.base.baseapp.model.ExampleData;
import com.example.base.baseapp.module.common.BasePresenter;
import com.example.base.baseapp.repository.ExampleDataRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;

@ActivityScope
public class MainPresenter extends BasePresenter<MainPresenter.UI> {

    private final Scheduler uiScheduler;
    private final Scheduler ioScheduler;
    private final Scheduler computationScheduler;
    private ExampleDataRepository exampleDataRepository;

    @Inject
    public MainPresenter(@UiScheduler Scheduler uiScheduler, @IOScheduler Scheduler ioScheduler, @ComputationScheduler Scheduler computationScheduler, ExampleDataRepository exampleDataRepository) {
        this.uiScheduler = uiScheduler;
        this.ioScheduler = ioScheduler;
        this.computationScheduler = computationScheduler;
        this.exampleDataRepository = exampleDataRepository;
    }

    @Override
    public void attach(UI ui) {
        super.attach(ui);
    }

    public void populateDatabase() {
    }

    public void loadAllData() {
        Completable insertExampleData = exampleDataRepository.insertExampleData(ExampleData.populateData()).subscribeOn(ioScheduler);
        Single<List<ExampleData>> getExampleData = exampleDataRepository.getAllExampleData()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
        addDisposable(insertExampleData.andThen(getExampleData).subscribe(exampleDataList -> sendSticky(ui -> ui.showAllExampleData(exampleDataList))));
    }

    public interface UI {
        void showAllExampleData(List<ExampleData> exampleDataList);
    }
}
