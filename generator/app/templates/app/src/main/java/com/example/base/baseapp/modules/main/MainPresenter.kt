package com.example.base.baseapp.modules.main

import com.example.base.baseapp.dagger.annotation.ComputationScheduler
import com.example.base.baseapp.dagger.annotation.IOScheduler
import com.example.base.baseapp.dagger.annotation.UiScheduler
import com.example.base.baseapp.model.ExampleData
import com.example.base.baseapp.modules.base.BasePresenter
import com.example.base.baseapp.modules.base.BaseUI
import com.example.base.baseapp.repository.ExampleDataRepository

import javax.inject.Inject

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single

class MainPresenter @Inject
constructor(@param:UiScheduler private val uiScheduler: Scheduler, @param:IOScheduler private val ioScheduler: Scheduler, @param:ComputationScheduler private val computationScheduler: Scheduler, private val exampleDataRepository: ExampleDataRepository) : BasePresenter<MainPresenter.UI>() {

    fun populateDatabase() {}

    fun loadAllData() {
        val insertExampleData = exampleDataRepository.insertExampleData(ExampleData.populateData()).subscribeOn(ioScheduler)
        val getExampleData = exampleDataRepository.allExampleData
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
        addDisposable(insertExampleData.andThen(getExampleData).subscribe { exampleDataList -> sendSticky({ ui -> ui.showAllExampleData(exampleDataList) }) })
    }

    interface UI : BaseUI {
        fun showAllExampleData(exampleDataList: List<ExampleData>)
    }
}
