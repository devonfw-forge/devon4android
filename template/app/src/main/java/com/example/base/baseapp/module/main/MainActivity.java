package com.example.base.baseapp.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.base.baseapp.R;
import com.example.base.baseapp.dagger.ActivityComponent;
import com.example.base.baseapp.model.ExampleData;
import com.example.base.baseapp.module.common.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class MainActivity extends BaseActivity<MainPresenter> implements MainPresenter.UI {
    @Inject
    MainPresenter presenter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.populateDatabase();
        presenter.loadAllData();
    }

    @Override
    protected MainPresenter getPresenter() {
        return presenter;
    }

    protected void injectActivity(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    public void showAllExampleData(List<ExampleData> exampleDataList) {
        MainAdapter mainAdapter = new MainAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mainAdapter);
        mainAdapter.setElements(exampleDataList);
    }
}
