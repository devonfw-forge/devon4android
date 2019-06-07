package com.example.base.baseapp.module.common;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.base.baseapp.MyApplication;
import com.example.base.baseapp.dagger.ActivityComponent;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    private ActivityComponent component;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BaseActivity() {
    }

    public void onStart() {
        super.onStart();
        this.getPresenter().attach(this);
    }

    public void onStop() {
        super.onStop();
        this.getPresenter().detach();
        this.compositeDisposable.clear();
    }

    protected void addDisposable(Disposable disposable) {
        this.compositeDisposable.add(disposable);
    }

    public Object onRetainCustomNonConfigurationInstance() {
        return this.getComponent();
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutResource());
        ButterKnife.bind(this);
        this.injectActivity(getComponent());
    }

    public ActivityComponent getComponent() {
        if (component == null) {
            component = (ActivityComponent) getLastCustomNonConfigurationInstance();
        }
        if (component == null) {
            MyApplication application = (MyApplication) getApplicationContext();
            component = application.getAppComponent().nonConfiguration();
        }
        return component;
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    protected abstract T getPresenter();

    protected abstract void injectActivity(ActivityComponent component);
}
