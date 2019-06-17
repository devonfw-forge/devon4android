package com.example.base.baseapp.modules.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

import com.example.base.baseapp.dagger.BaseActivityComponent

import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.base.baseapp.BaseApplication
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BaseFragment<T : BasePresenter<BaseUI>, E : BaseActivityComponent> : Fragment(), BaseUI {

    private var unbinder: Unbinder? = null
    private val compositeDisposable = CompositeDisposable()

    protected abstract val component: E

    protected abstract val presenter: T

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        unbinder = ButterKnife.bind(this, view)
        injectFragment(component)
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detach()
        compositeDisposable.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
        //LeakCanary lifecycle for fragments: https://github.com/square/leakcanary/wiki/Customizing-LeakCanary#watching-objects-with-a-lifecycle
//        (activity!!.applicationContext as BaseApplication).getRefWatcher().watch(this)
    }

    protected abstract fun injectFragment(component: E)
}
