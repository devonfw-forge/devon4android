package com.example.base.baseapp.module.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.base.baseapp.MyApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public abstract class BaseFragment<T extends BasePresenter, E> extends Fragment {

    private Unbinder unbinder;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        injectFragment(getComponent());
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().attach(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().detach();
        compositeDisposable.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        //LeakCanary lifecycle for fragments: https://github.com/square/leakcanary/wiki/Customizing-LeakCanary#watching-objects-with-a-lifecycle
        ((MyApplication) getActivity().getApplicationContext()).getRefWatcher().watch(this);
    }

    protected abstract E getComponent();

    protected abstract T getPresenter();

    protected abstract void injectFragment(E component);
}
