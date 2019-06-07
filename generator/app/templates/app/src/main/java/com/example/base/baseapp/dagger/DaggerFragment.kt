package com.example.base.baseapp.dagger

import com.example.base.baseapp.modules.base.BaseFragment
import com.example.base.baseapp.modules.base.BasePresenter

abstract class DaggerFragment<T : BasePresenter<*>> : BaseFragment<T, ActivityComponent>() {

    override fun getComponent(): ActivityComponent {
        return (activity as DaggerActivity<*>).component
    }
}
