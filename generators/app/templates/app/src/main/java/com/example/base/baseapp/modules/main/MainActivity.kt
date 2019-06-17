package com.example.base.baseapp.modules.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import com.example.base.baseapp.R
import com.example.base.baseapp.dagger.ActivityComponent
import com.example.base.baseapp.dagger.DaggerActivity
import com.example.base.baseapp.model.ExampleData

import javax.inject.Inject

import butterknife.BindView


class MainActivity : DaggerActivity<MainPresenter>(), MainPresenter.UI {
    @Inject
    override var presenter: MainPresenter = null
        internal set

    @BindView(R.id.recycler_view)
    internal var recyclerView: RecyclerView? = null

    override val layoutResource: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter!!.populateDatabase()
        presenter!!.loadAllData()
    }

    override fun injectActivity(component: ActivityComponent) {
        component.inject(this)
    }

    override fun showAllExampleData(exampleDataList: List<ExampleData>) {
        val mainAdapter = MainAdapter()
        recyclerView!!.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.adapter = mainAdapter
        mainAdapter.setElements(exampleDataList)
    }
}
