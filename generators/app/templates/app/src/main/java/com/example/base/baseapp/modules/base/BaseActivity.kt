package com.example.base.baseapp.modules.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity

import com.example.base.baseapp.dagger.BaseActivityComponent

import butterknife.ButterKnife
import com.example.base.baseapp.dagger.ActivityComponent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity<T : BasePresenter<BaseUI>, E : BaseActivityComponent> : AppCompatActivity(), BaseUI {
    private var component: ActivityComponent? = null
    private val compositeDisposable = CompositeDisposable()

    @get:LayoutRes
    protected abstract val layoutResource: Int

    protected abstract val presenter: T

    public override fun onStart() {
        super.onStart()
        this.presenter.attach(this)
    }

    public override fun onStop() {
        super.onStop()
        this.presenter.detach()
        this.compositeDisposable.clear()
    }

    protected fun addDisposable(disposable: Disposable) {
        this.compositeDisposable.add(disposable)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return this.getComponent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(this.layoutResource)
        ButterKnife.bind(this)
        this.injectActivity(this.getComponent())
    }

    abstract fun getComponent(): E

    protected abstract fun injectActivity(component: E)
}
